package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.model.CreatePlanRequest
import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

/**
 * 약속 생성 UseCase
 */
class CreatePlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(request: CreatePlanRequest): Result<Plan> {
        return planRepository.createPlan(request)
    }
}





