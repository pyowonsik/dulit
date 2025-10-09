// feature/couple/domain/usecase/ConnectMatchingSocketUseCase.kt
package com.example.dulit.feature.couple.domain.usecase

import com.example.dulit.feature.couple.domain.repository.CoupleMatchingRepository
import com.example.dulit.feature.couple.domain.repository.MatchingSocketState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectMatchingSocketUseCase @Inject constructor(
    private val coupleMatchingRepository: CoupleMatchingRepository
) {
    operator fun invoke(userId: String): Flow<MatchingSocketState> {
        return coupleMatchingRepository.connectSocket(userId)
    }
}