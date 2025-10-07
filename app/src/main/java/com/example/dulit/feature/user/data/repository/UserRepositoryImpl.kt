// feature/user/data/repository/UserRepositoryImpl.kt
package com.example.dulit.feature.user.data.repository

import android.util.Log
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.user.data.api.AuthApi
import com.example.dulit.feature.user.data.model.KakaoLoginRequest
import com.example.dulit.feature.user.data.model.UserDto
import com.example.dulit.feature.user.domain.model.User
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage  // com.example.dulit.core.local.TokenStorage 주입
) : UserRepository {

    override suspend fun kakaoLogin(kakaoToken: String): Result<User> {
        return try {
            val response = authApi.kakaoLogin(
                KakaoLoginRequest(kakaoAccessToken = kakaoToken)
            )

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Log.i("UserRepository", "로그인 성공: ${body.user.name}")
                Log.i("UserRepository", "JWT: ${body.accessToken}")

                // JWT 토큰 로컬 저장
                tokenStorage.saveAccessToken(body.accessToken)
                tokenStorage.saveRefreshToken(body.refreshToken)

                Log.d("Login Token Info [Access]",tokenStorage.getAccessToken().toString())
                Log.d("Login Token Info [Refresh]",tokenStorage.getRefreshToken().toString())

                Result.success(body.user.toDomain())
            } else {
                Log.e("UserRepository", "로그인 실패: ${response.code()}")
                Result.failure(Exception("로그인 실패"))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "네트워크 에러", e)
            Result.failure(e)
        }
    }
}

// Mapper
private fun UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email
)