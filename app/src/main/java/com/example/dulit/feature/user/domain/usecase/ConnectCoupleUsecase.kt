package com.example.dulit.feature.user.domain.usecase

//import com.example.dulit.feature.user.data.model.ConnectCoupleResponse
import com.example.dulit.feature.user.domain.repository.UserRepository
import javax.inject.Inject

class ConnectCoupleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(partnerId: String): Result<Boolean> {
        return userRepository.connectCouple(partnerId)
    }
}
