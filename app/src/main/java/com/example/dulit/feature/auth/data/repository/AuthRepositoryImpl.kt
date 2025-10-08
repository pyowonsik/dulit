// feature/user/data/repository/UserRepositoryImpl.kt
package com.example.dulit.feature.auth.data.repository

import android.util.Log
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.auth.data.api.AuthApi
import com.example.dulit.feature.auth.data.model.KakaoLoginRequest
import com.example.dulit.feature.auth.data.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun kakaoLogin(kakaoToken: String): Result<KakaoLoginResponse> {
        return try {
            val response = authApi.kakaoLogin(
                KakaoLoginRequest(kakaoAccessToken = kakaoToken)
            )
            // 👇 RAW JSON 전체 로그
            Log.d("AuthRepositoryImpl", "=== RAW RESPONSE ===")
            Log.d("AuthRepositoryImpl", "Status Code: ${response.code()}")
            Log.d("AuthRepositoryImpl", "Response Body: ${response.body()}")
            Log.d("AuthRepositoryImpl", "Raw JSON: ${response.raw()}")

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!

                Log.i("AuthRepositoryImpl", "로그인 성공: ${body.user.name}")
                Log.i("AuthRepositoryImpl", "JWT: ${body.accessToken}")

                // 👇 JWT 토큰 + socialId 저장
                tokenStorage.saveAccessToken(body.accessToken)
                tokenStorage.saveRefreshToken(body.refreshToken)
                tokenStorage.saveSocialId(body.user.socialId)  // 👈 추가!

                  Log.d("AuthRepositoryImpl [User]", body.toString())
                //  UserDto(id=2, name=표원식, email=qqrtyu@gmail.com, socialId=3904586188, isConnected=false)

                Result.success(body)
            } else {
                Log.e("AuthRepositoryImpl", "로그인 실패: ${response.code()}")
                Result.failure(Exception("로그인 실패"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "네트워크 에러", e)
            Result.failure(e)
        }
    }
}
