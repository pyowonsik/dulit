// feature/auth/domain/usecase/RotateAccessTokenUseCase.kt
package com.example.dulit.feature.auth.domain.usecase

import com.example.dulit.feature.auth.domain.model.RotateAccessTokenResponse
import com.example.dulit.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RotateAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<RotateAccessTokenResponse> {
        return authRepository.rotateAccessToken()
    }
}