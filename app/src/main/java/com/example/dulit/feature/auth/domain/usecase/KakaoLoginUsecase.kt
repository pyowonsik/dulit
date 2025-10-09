package com.example.dulit.feature.auth.domain.usecase

import com.example.dulit.feature.auth.domain.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.model.KakaoLoginRquest
import com.example.dulit.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: KakaoLoginRquest): Result<KakaoLoginResponse> {
        return authRepository.kakaoLogin(request)
    }
}