package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

/**
 * 약속 단건 조회 UseCase
 */
class GetPlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: Int): Result<Plan> {
        return planRepository.findOnePlan(planId)
    }
}



