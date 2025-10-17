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

    private val _loginState = MutableStateFlow<LoginState>(LoginState.CheckingAutoLogin)
    val loginState: StateFlow<LoginState> = _loginState

    /**
     * 자동 로그인
     * 
     * 동작:
     * 1. AccessToken, RefreshToken 존재 여부 확인
     * 2. getMeUseCase()로 사용자 정보 조회
     * 3. 만약 AccessToken 만료 시 → TokenAuthenticator가 자동으로 갱신
     * 
     * 책임 분리:
     * - LoginViewModel: 토큰 존재 여부만 확인
     * - TokenAuthenticator: 401 에러 시 자동 토큰 갱신
     */
    fun autoLogin() {
        viewModelScope.launch {
            val accessToken = tokenStorage.getAccessToken()
            val refreshToken = tokenStorage.getRefreshToken()
            
            Log.d("LoginViewModel [autoLogin]", "AccessToken 존재: ${!accessToken.isNullOrEmpty()}")
            Log.d("LoginViewModel [autoLogin]", "RefreshToken 존재: ${!refreshToken.isNullOrEmpty()}")

            // ✅ 둘 다 있어야 자동 로그인 가능
            if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                Log.d("LoginViewModel [autoLogin]", "❌ 토큰 없음 → NeedLogin")
                _loginState.value = LoginState.NeedLogin
                return@launch
            }

            // ✅ 사용자 정보 조회
            // (AccessToken 만료 시 TokenAuthenticator가 자동으로 갱신)
            val result = getMeUseCase()
            Log.d("LoginViewModel [autoLogin]", "getMeUseCase 결과: $result")

            _loginState.value = if (result.isSuccess) {
                val user = result.getOrThrow()

                val response = KakaoLoginResponse(
                    user = user,
                    isCouple = user.coupleId != null,
                    accessToken = tokenStorage.getAccessToken() ?: "",  // ✅ 갱신된 토큰 사용
                    refreshToken = refreshToken
                )

                if (response.isCouple) {
                    Log.i("LoginViewModel [autoLogin]", "✅ 자동 로그인 + 커플 연결 → Home")
                    LoginState.AlreadyConnected(response)
                } else {
                    Log.i("LoginViewModel [autoLogin]", "✅ 자동 로그인 + 커플 미연결 → 연결 모달")
                    LoginState.NeedConnection(response)
                }
            } else {
                Log.e("LoginViewModel [autoLogin]", "❌ 사용자 정보 조회 실패 → 로그인 필요")
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
