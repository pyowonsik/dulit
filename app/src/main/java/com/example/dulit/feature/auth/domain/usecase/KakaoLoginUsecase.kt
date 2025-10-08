package com.example.dulit.feature.auth.domain.usecase

import com.example.dulit.feature.auth.data.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.repository.AuthRepository
import com.example.dulit.feature.user.domain.model.User
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(kakaoToken: String): Result<KakaoLoginResponse> {
        return authRepository.kakaoLogin(kakaoToken)
    }
}