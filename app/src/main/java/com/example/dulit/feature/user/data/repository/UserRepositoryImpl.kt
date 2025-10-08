// feature/user/data/repository/UserRepositoryImpl.kt
package com.example.dulit.feature.user.data.repository

import android.util.Log
import com.example.dulit.feature.user.data.api.UserApi
//import com.example.dulit.feature.user.data.model.ConnectCoupleResponse
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun connectCouple(partnerId: String): Result<Boolean> {
        return try {
            val response = userApi.connectCouple(partnerId)
            if (response.isSuccessful && response.body() != null) {
                Log.d("UserRepository", "✅ 커플 연결 성공")
                Result.success(response.body()!!)
            } else {
                Log.e("UserRepository", "❌ 커플 연결 실패: ${response.code()}")
                Result.failure(Exception("Failed to connect: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "connectCouple error", e)
            Result.failure(e)
        }
    }
}