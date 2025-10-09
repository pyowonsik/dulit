// feature/user/domain/usecase/GetMeUseCase.kt
package com.example.dulit.feature.user.domain.usecase

import com.example.dulit.feature.user.domain.model.User
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.getMe()
    }
}