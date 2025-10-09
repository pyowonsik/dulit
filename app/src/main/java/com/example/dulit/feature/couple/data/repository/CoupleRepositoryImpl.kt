// feature/couple/data/repository/CoupleRepositoryImpl.kt
package com.example.dulit.feature.couple.data.repository

import android.util.Log
import com.example.dulit.feature.couple.data.api.CoupleApi
import com.example.dulit.feature.couple.domain.repository.CoupleRepository
import javax.inject.Inject

class CoupleRepositoryImpl @Inject constructor(
    private val coupleApi: CoupleApi
) : CoupleRepository {

    override suspend fun connectCouple(partnerId: String): Result<Boolean> {
        return try {
            val response = coupleApi.connectCouple(partnerId)
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