// feature/couple/domain/repository/CoupleRepository.kt
package com.example.dulit.feature.couple.domain.repository

interface CoupleRepository {
    suspend fun connectCouple(partnerId: String): Result<Boolean>

    // 나중에 추가될 수 있는 메서드들
    // suspend fun getCoupleInfo(): Result<Couple>
    // suspend fun disconnectCouple(): Result<Boolean>
}