// feature/couple/domain/repository/CoupleRepository.kt
package com.example.dulit.feature.couple.domain.repository

interface CoupleRepository {
    suspend fun connectCouple(partnerId: String): Result<Boolean>
}