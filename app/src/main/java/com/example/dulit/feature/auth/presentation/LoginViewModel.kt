package com.example.dulit.feature.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.auth.domain.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.model.KakaoLoginRquest
import com.example.dulit.feature.auth.domain.usecase.KakaoLoginUseCase
import com.example.dulit.feature.user.domain.usecase.GetMeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val getMeUseCase: GetMeUseCase,
    val tokenStorage: TokenStorage
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.CheckingAutoLogin)  // 👈 초기 상태
    val loginState: StateFlow<LoginState> = _loginState

    fun autoLogin() {
        viewModelScope.launch {
            // 👇 이미 CheckingAutoLogin 상태
            val accessToken = tokenStorage.getAccessToken()
            Log.d("LoginViewModel [autoLogin]", "토큰: $accessToken")

            if (accessToken.isNullOrEmpty()) {
                Log.d("LoginViewModel [autoLogin]", "❌ 토큰 없음 → NeedLogin")
                _loginState.value = LoginState.NeedLogin
                return@launch
            }

            val result = getMeUseCase()
            Log.d("LoginViewModel [autoLogin]", "getMeUseCase 결과: $result")

            _loginState.value = if (result.isSuccess) {
                val user = result.getOrThrow()

                val response = KakaoLoginResponse(
                    user = user,
                    isCouple = user.coupleId != null,
                    accessToken = accessToken,
                    refreshToken = tokenStorage.getRefreshToken() ?: ""
                )

                if (response.isCouple) {
                    Log.i("LoginViewModel [autoLogin]", "✅ 자동 로그인 + 커플 연결 → Home")
                    LoginState.AlreadyConnected(response)
                } else {
                    Log.i("LoginViewModel [autoLogin]", "✅ 자동 로그인 + 커플 미연결 → 연결 모달")
                    LoginState.NeedConnection(response)
                }
            } else {
                Log.e("LoginViewModel [autoLogin]", "❌ 토큰 검증 실패 → 로그인 필요")
                tokenStorage.clearTokens()
                LoginState.NeedLogin
            }
        }
    }

    fun kakaoLogin(kakaoToken: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading  // 👈 수동 로그인 로딩

            val request = KakaoLoginRquest(kakaoToken)
            val result = kakaoLoginUseCase(request)

            Log.d("LoginViewModel", "kakaoLogin: $result")

            _loginState.value = if (result.isSuccess) {
                val loginResponse = result.getOrThrow()

                if (loginResponse.isCouple) {
                    Log.i("LoginViewModel", "✅ 커플 연결됨 → Home 이동")
                    LoginState.AlreadyConnected(loginResponse)
                } else {
                    Log.i("LoginViewModel", "❌ 커플 미연결 → 연결 화면 이동")
                    LoginState.NeedConnection(loginResponse)
                }
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "알 수 없는 오류")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.NeedLogin
    }
}

sealed class LoginState {
    object CheckingAutoLogin : LoginState()  // 👈 자동 로그인 체크 중
    object NeedLogin : LoginState()
    object Loading : LoginState()  // 수동 로그인 중
    data class AlreadyConnected(val response: KakaoLoginResponse) : LoginState()
    data class NeedConnection(val response: KakaoLoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}
