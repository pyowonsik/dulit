package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.CreateAnniversaryRequest
import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

/**
 * 기념일 작성 UseCase
 */
class CreateAnniversaryUseCase @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) {
    suspend operator fun invoke(request: CreateAnniversaryRequest): Result<Anniversary> {
        return anniversaryRepository.createAnniversary(request)
    }
}



