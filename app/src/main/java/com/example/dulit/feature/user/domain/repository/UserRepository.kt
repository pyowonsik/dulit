// feature/user/domain/repository/UserRepository.kt
package com.example.dulit.feature.user.domain.repository

//import com.example.dulit.feature.user.data.model.ConnectCoupleResponse

interface UserRepository {
    suspend fun connectCouple(partnerId: String): Result<Boolean>
}
