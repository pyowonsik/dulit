// feature/couple/domain/usecase/ConnectCoupleUseCase.kt
package com.example.dulit.feature.couple.domain.usecase

import com.example.dulit.feature.couple.domain.repository.CoupleRepository
import javax.inject.Inject

class ConnectCoupleUseCase @Inject constructor(
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(partnerId: String): Result<Boolean> {
        return coupleRepository.connectCouple(partnerId)
    }
}