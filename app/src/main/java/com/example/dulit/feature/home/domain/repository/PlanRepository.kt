package com.example.dulit.feature.home.domain.repository

import com.example.dulit.feature.home.domain.model.CreatePlanRequest
import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.model.UpdatePlanRequest

/**
 * Plan Repository Interface
 */
interface PlanRepository {
    
    /**
     * 약속 생성
     */
    suspend fun createPlan(request: CreatePlanRequest): Result<Plan>
    
    /**
     * 전체 약속 조회
     * @param topic 주제 검색 (optional)
     * @return 시간순(ASC)으로 정렬된 전체 약속 리스트
     */
    suspend fun findAllPlans(
        topic: String? = null
    ): Result<List<Plan>>
    
    /**
     * 약속 단건 조회
     */
    suspend fun findOnePlan(planId: Int): Result<Plan>
    
    /**
     * 약속 수정
     */
    suspend fun updatePlan(planId: Int, request: UpdatePlanRequest): Result<Plan>
    
    /**
     * 약속 삭제
     * @return 삭제된 planId
     */
    suspend fun deletePlan(planId: Int): Result<Int>
}
