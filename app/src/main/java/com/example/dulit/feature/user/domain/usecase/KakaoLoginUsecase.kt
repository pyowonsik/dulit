package com.example.dulit.feature.user.domain.usecase

import com.example.dulit.feature.user.domain.model.User
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(kakaoToken: String): Result<User> {
        return userRepository.kakaoLogin(kakaoToken)
    }
}