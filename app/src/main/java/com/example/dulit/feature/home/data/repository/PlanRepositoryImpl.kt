package com.example.dulit.feature.home.data.repository

import android.util.Log
import com.example.dulit.feature.home.data.remote.api.PlanApi
import com.example.dulit.feature.home.data.remote.dto.toDomain
import com.example.dulit.feature.home.data.remote.dto.toDto
import com.example.dulit.feature.home.domain.model.CreatePlanRequest
import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.model.UpdatePlanRequest
import com.example.dulit.feature.home.domain.repository.PlanRepository
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val planApi: PlanApi
) : PlanRepository {

    override suspend fun createPlan(request: CreatePlanRequest): Result<Plan> = runCatching {
        Log.d("PlanRepository", "약속 생성 API 호출")
        val response = planApi.createPlan(request.toDto())
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PlanRepository", "❌ 약속 생성 실패: ${response.code()}")
            throw Exception("약속 생성 실패: ${response.code()}")
        }
        val plan = response.body()!!.toDomain()
        Log.d("PlanRepository", "✅ 약속 생성 성공: ${plan.topic}")
        plan
    }.onFailure { e ->
        Log.e("PlanRepository", "약속 생성 에러", e)
    }

    override suspend fun findAllPlans(
        topic: String?
    ): Result<List<Plan>> = runCatching {
        Log.d("PlanRepository", "약속 전체 조회 API 호출${if (topic != null) " (topic=$topic)" else ""}")
        val response = planApi.findAllPlans(topic)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PlanRepository", "❌ 약속 조회 실패: ${response.code()}")
            throw Exception("약속 조회 실패: ${response.code()}")
        }
        val plans = response.body()!!.map { it.toDomain() }
        Log.d("PlanRepository", "✅ 약속 ${plans.size}개 조회 성공")
        plans
    }.onFailure { e ->
        Log.e("PlanRepository", "약속 조회 에러", e)
    }

    override suspend fun findOnePlan(planId: Int): Result<Plan> = runCatching {
        Log.d("PlanRepository", "약속 단건 조회 API 호출 (planId=$planId)")
        val response = planApi.findOnePlan(planId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PlanRepository", "❌ 약속 조회 실패: ${response.code()}")
            throw Exception("약속 조회 실패: ${response.code()}")
        }
        val plan = response.body()!!.toDomain()
        Log.d("PlanRepository", "✅ 약속 조회 성공: ${plan.topic}")
        plan
    }.onFailure { e ->
        Log.e("PlanRepository", "약속 조회 에러", e)
    }

    override suspend fun updatePlan(planId: Int, request: UpdatePlanRequest): Result<Plan> = runCatching {
        Log.d("PlanRepository", "약속 수정 API 호출 (planId=$planId)")
        val response = planApi.updatePlan(planId, request.toDto())
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PlanRepository", "❌ 약속 수정 실패: ${response.code()}")
            throw Exception("약속 수정 실패: ${response.code()}")
        }
        val plan = response.body()!!.toDomain()
        Log.d("PlanRepository", "✅ 약속 수정 성공: ${plan.topic}")
        plan
    }.onFailure { e ->
        Log.e("PlanRepository", "약속 수정 에러", e)
    }

    override suspend fun deletePlan(planId: Int): Result<Int> = runCatching {
        Log.d("PlanRepository", "약속 삭제 API 호출 (planId=$planId)")
        val response = planApi.deletePlan(planId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PlanRepository", "❌ 약속 삭제 실패: ${response.code()}")
            throw Exception("약속 삭제 실패: ${response.code()}")
        }
        val deletedId = response.body()!!
        Log.d("PlanRepository", "✅ 약속 삭제 성공 (deletedId=$deletedId)")
        deletedId
    }.onFailure { e ->
        Log.e("PlanRepository", "약속 삭제 에러", e)
    }
}
