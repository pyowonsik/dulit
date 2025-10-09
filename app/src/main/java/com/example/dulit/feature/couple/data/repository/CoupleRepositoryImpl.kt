// feature/couple/data/repository/CoupleRepositoryImpl.kt
package com.example.dulit.feature.couple.data.repository

import android.util.Log
import com.example.dulit.feature.couple.data.api.CoupleApi
import com.example.dulit.feature.couple.domain.repository.CoupleRepository
import javax.inject.Inject

class CoupleRepositoryImpl @Inject constructor(
    private val coupleApi: CoupleApi
) : CoupleRepository {

    override suspend fun connectCouple(partnerId: String): Result<Boolean> = runCatching {
        val response = coupleApi.connectCouple(partnerId)

        if (!response.isSuccessful || response.body() == null) {
            Log.e("CoupleRepository", "❌ 커플 연결 실패: ${response.code()}")
            throw Exception("Failed to connect: ${response.code()}")
        }

        Log.d("CoupleRepository", "✅ 커플 연결 성공")

        // ⭐ 마지막 표현식이 자동으로 Result.success()
        response.body()!!
    }.onFailure { e ->
        Log.e("CoupleRepository", "커플 연결 에러", e)
    }
}