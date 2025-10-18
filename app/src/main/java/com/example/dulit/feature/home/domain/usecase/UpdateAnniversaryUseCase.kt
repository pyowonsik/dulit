package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.UpdateAnniversaryRequest
import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

/**
 * 기념일 수정 UseCase
 */
class UpdateAnniversaryUseCase @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) {
    suspend operator fun invoke(anniversaryId: Int, request: UpdateAnniversaryRequest): Result<Anniversary> {
        return anniversaryRepository.updateAnniversary(anniversaryId, request)
    }
}





