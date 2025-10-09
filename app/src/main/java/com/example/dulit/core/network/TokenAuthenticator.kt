// core/network/TokenAuthenticator.kt
package com.example.dulit.core.network

import android.util.Log
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.auth.domain.usecase.RotateAccessTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val rotateAccessTokenUseCase: dagger.Lazy<RotateAccessTokenUseCase>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("TokenAuthenticator", "401 에러 감지 - 토큰 갱신 시작")

        // ⭐ 재시도 방지 (무한 루프 차단)
        if (response.request.header("X-Retry-Auth") != null) {
            Log.w("TokenAuthenticator", "재시도 실패 - 로그아웃")
            tokenStorage.clearTokens()
            return null
        }

//        // ⭐ 추가: 토큰 갱신 API 자체가 실패한 경우 재시도 안 함 (무한 루프 차단)
//        if (response.request.url.encodedPath == "/auth/token/access") {
//            Log.w("TokenAuthenticator", "토큰 갱신 API 실패 - 로그아웃")
//            tokenStorage.clearTokens()
//            return null
//        }

        val refreshToken = tokenStorage.getRefreshToken()

        if (refreshToken.isNullOrEmpty()) {
            Log.w("TokenAuthenticator", "리프레시 토큰 없음")
            tokenStorage.clearTokens()
            return null
        }

        return runBlocking {
            try {
                val result = rotateAccessTokenUseCase.get().invoke()

                result.fold(
                    onSuccess = { tokens ->
                        Log.d("TokenAuthenticator", "토큰 갱신 성공")
                        tokenStorage.saveAccessToken(tokens.accessToken)

                        response.request.newBuilder()
                            .header("Authorization", "Bearer ${tokens.accessToken}")
                            .header("X-Retry-Auth", "true")
                            .build()
                    },
                    onFailure = { error ->
                        Log.e("TokenAuthenticator", "토큰 갱신 실패: ${error.message}")
                        tokenStorage.clearTokens()
                        null
                    }
                )
            } catch (e: Exception) {
                Log.e("TokenAuthenticator", "토큰 갱신 중 예외 발생", e)
                tokenStorage.clearTokens()
                null
            }
        }
    }
}