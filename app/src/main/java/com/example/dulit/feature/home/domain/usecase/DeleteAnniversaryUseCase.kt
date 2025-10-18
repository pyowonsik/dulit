package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

/**
 * 기념일 삭제 UseCase
 * @return 삭제된 anniversaryId
 */
class DeleteAnniversaryUseCase @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) {
    suspend operator fun invoke(anniversaryId: Int): Result<Int> {
        return anniversaryRepository.deleteAnniversary(anniversaryId)
    }
}





