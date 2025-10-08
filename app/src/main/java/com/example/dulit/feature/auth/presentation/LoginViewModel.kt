// feature/auth/presentation/LoginViewModel.kt
package com.example.dulit.feature.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.auth.data.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.usecase.KakaoLoginUseCase
import com.example.dulit.feature.user.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    val tokenStorage: TokenStorage  // 👈 추가 (모달에서 socialId 가져올 때 사용)
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState
    fun kakaoLogin(kakaoToken: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = kakaoLoginUseCase(kakaoToken)

            Log.d("LoginViewModel", "kakaoLogin: $result")

            _loginState.value = if (result.isSuccess) {
                val loginResponse = result.getOrThrow()

                // 💡 커플 연결 여부에 따라 분기
                if (loginResponse.isCouple) {
                    Log.i("LoginViewModel", "✅ 커플 연결됨 → Home 이동")
                    LoginState.AlreadyConnected(loginResponse)  // 👈 수정
                } else {
                    Log.i("LoginViewModel", "❌ 커플 미연결 → 연결 화면 이동")
                    LoginState.NeedConnection(loginResponse)  // 👈 수정
                }
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "알 수 없는 오류")
            }
        }
    }
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class AlreadyConnected(val response: KakaoLoginResponse) : LoginState()  // 👈 타입 변경
    data class NeedConnection(val response: KakaoLoginResponse) : LoginState()    // 👈 타입 변경
    data class Error(val message: String) : LoginState()
}