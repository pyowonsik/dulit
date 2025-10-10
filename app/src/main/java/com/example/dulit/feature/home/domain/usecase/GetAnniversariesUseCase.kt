package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

/**
 * 전체 기념일 조회 UseCase
 */
class GetAnniversariesUseCase @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) {
    suspend operator fun invoke(
        page: Int? = null,
        take: Int? = null,
        order: String? = null,
        title: String? = null
    ): Result<List<Anniversary>> {
        return anniversaryRepository.findAllAnniversaries(page, take, order, title)
    }
}

