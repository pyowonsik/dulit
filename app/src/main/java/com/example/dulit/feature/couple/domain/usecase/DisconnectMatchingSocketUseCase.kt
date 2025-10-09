// feature/couple/domain/usecase/DisconnectMatchingSocketUseCase.kt
package com.example.dulit.feature.couple.domain.usecase

import com.example.dulit.feature.couple.domain.repository.CoupleMatchingRepository
import javax.inject.Inject

class DisconnectMatchingSocketUseCase @Inject constructor(
    private val coupleMatchingRepository: CoupleMatchingRepository
) {
    operator fun invoke() {
        coupleMatchingRepository.disconnectSocket()
    }
}