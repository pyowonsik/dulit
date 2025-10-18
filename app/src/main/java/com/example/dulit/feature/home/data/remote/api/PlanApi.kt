package com.example.dulit.feature.home.data.remote.api

import com.example.dulit.feature.home.data.remote.dto.CreatePlanRequestDto
import com.example.dulit.feature.home.data.remote.dto.PlanResponseDto
import com.example.dulit.feature.home.data.remote.dto.UpdatePlanRequestDto
import retrofit2.Response
import retrofit2.http.*

interface PlanApi {

    /**
     * 약속 생성
     * POST /couple/plan
     */
    @POST("/couple/plan")
    suspend fun createPlan(
        @Body request: CreatePlanRequestDto
    ): Response<PlanResponseDto>

    /**
     * 전체 약속 조회
     * GET /couple/plan?topic=검색어 (선택사항)
     * 백엔드에서 시간순(ASC)으로 자동 정렬됨
     */
    @GET("/couple/plan")
    suspend fun findAllPlans(
        @Query("topic") topic: String? = null
    ): Response<List<PlanResponseDto>>

    /**
     * 약속 단건 조회
     * GET /couple/plan/:planId
     */
    @GET("/couple/plan/{planId}")
    suspend fun findOnePlan(
        @Path("planId") planId: Int
    ): Response<PlanResponseDto>

    /**
     * 약속 수정
     * PATCH /couple/plan/:planId
     */
    @PATCH("/couple/plan/{planId}")
    suspend fun updatePlan(
        @Path("planId") planId: Int,
        @Body request: UpdatePlanRequestDto
    ): Response<PlanResponseDto>

    /**
     * 약속 삭제
     * DELETE /couple/plan/:planId
     * @return 삭제된 planId
     */
    @DELETE("/couple/plan/{planId}")
    suspend fun deletePlan(
        @Path("planId") planId: Int
    ): Response<Int>
}
