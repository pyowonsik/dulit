// feature/user/data/repository/UserRepositoryImpl.kt
package com.example.dulit.feature.user.data.repository

import android.util.Log
import com.example.dulit.feature.user.data.api.UserApi
import com.example.dulit.feature.user.data.model.toDomain
import com.example.dulit.feature.user.domain.model.User
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getMe(): Result<User> {
        return try {
            Log.d("UserRepository", "내 정보 조회 API 호출")

            val response = userApi.getMe()
            Log.d("UserRepository", "✅ 내 정보 조회 성공: ${response.name}")
            Log.d("UserRepository", "User: id=${response.id}, email=${response.email}, socialId=${response.socialId}")

            Result.success(response.toDomain())

        } catch (e: Exception) {
            Log.e("UserRepository", "❌ 내 정보 조회 실패", e)
            Result.failure(e)
        }
    }
}