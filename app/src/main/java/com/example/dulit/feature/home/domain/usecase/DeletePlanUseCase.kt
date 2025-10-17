package com.example.dulit.feature.home.domain.usecase

import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

/**
 * 약속 삭제 UseCase
 * @return 삭제된 planId
 */
class DeletePlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: Int): Result<Int> {
        return planRepository.deletePlan(planId)
    }
}



