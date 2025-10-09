// feature/user/domain/repository/UserRepository.kt
package com.example.dulit.feature.user.domain.repository

import com.example.dulit.feature.user.domain.model.User

interface UserRepository {
    suspend fun getMe(): Result<User>

    // 나중에 추가될 수 있는 메서드들
    // suspend fun updateProfile(name: String): Result<User>
    // suspend fun uploadProfileImage(imageUri: Uri): Result<String>
}