package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

/**
 * 전체 기념일 조회 UseCase
 * 백엔드에서 날짜순(ASC)으로 정렬된 전체 기념일을 반환
 */
class GetAnniversariesUseCase @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) {
    suspend operator fun invoke(
        title: String? = null
    ): Result<List<Anniversary>> {
        return anniversaryRepository.findAllAnniversaries(title)
    }
}





