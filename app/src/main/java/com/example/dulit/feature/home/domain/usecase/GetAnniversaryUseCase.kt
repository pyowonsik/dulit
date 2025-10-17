package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

/**
 * 단건 기념일 조회 UseCase
 */
class GetAnniversaryUseCase @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) {
    suspend operator fun invoke(anniversaryId: Int): Result<Anniversary> {
        return anniversaryRepository.findOneAnniversary(anniversaryId)
    }
}



