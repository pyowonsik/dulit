package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

/**
 * 전체 약속 조회 UseCase
 * 백엔드에서 시간순(ASC)으로 정렬된 전체 약속을 반환
 */
class GetPlansUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(
        topic: String? = null
    ): Result<List<Plan>> {
        return planRepository.findAllPlans(topic)
    }
}





