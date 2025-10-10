package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

/**
 * 전체 약속 조회 UseCase
 */
class GetPlansUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(
        page: Int? = null,
        take: Int? = null,
        order: String? = null,
        topic: String? = null
    ): Result<List<Plan>> {
        return planRepository.findAllPlans(page, take, order, topic)
    }
}

