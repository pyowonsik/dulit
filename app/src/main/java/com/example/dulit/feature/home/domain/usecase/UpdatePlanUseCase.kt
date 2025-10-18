package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.model.UpdatePlanRequest
import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

/**
 * 약속 수정 UseCase
 */
class UpdatePlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: Int, request: UpdatePlanRequest): Result<Plan> {
        return planRepository.updatePlan(planId, request)
    }
}





