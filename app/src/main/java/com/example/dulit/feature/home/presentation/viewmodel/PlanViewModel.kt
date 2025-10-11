package com.example.dulit.feature.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulit.feature.home.domain.model.Plan
import com.example.dulit.feature.home.domain.model.CreatePlanRequest
import com.example.dulit.feature.home.domain.model.UpdatePlanRequest
import com.example.dulit.feature.home.domain.usecase.CreatePlanUseCase
import com.example.dulit.feature.home.domain.usecase.DeletePlanUseCase
import com.example.dulit.feature.home.domain.usecase.GetPlansUseCase
import com.example.dulit.feature.home.domain.usecase.GetPlanUseCase
import com.example.dulit.feature.home.domain.usecase.UpdatePlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val createPlanUseCase: CreatePlanUseCase,
    private val getPlansUseCase: GetPlansUseCase,
    private val getPlanUseCase: GetPlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase
) : ViewModel() {

    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans.asStateFlow()

    private val _planState = MutableStateFlow<PlanState>(PlanState.Idle)
    val planState: StateFlow<PlanState> = _planState.asStateFlow()

    /**
     * 계획 생성
     */
    fun createPlan(
        topic: String,
        location: String,
        time: String
    ) {
        viewModelScope.launch {
            _planState.value = PlanState.Loading
            Log.d("PlanViewModel", "계획 생성 시작: topic=$topic, location=$location, time=$time")

            val request = CreatePlanRequest(topic = topic, location = location, time = time)
            val result = createPlanUseCase(request)

            result.onSuccess { plan ->
                Log.d("PlanViewModel", "✅ 계획 생성 성공: ${plan.topic}")
                _plans.value = _plans.value + plan
                _planState.value = PlanState.Success
            }.onFailure { e ->
                Log.e("PlanViewModel", "❌ 계획 생성 실패", e)
                _planState.value = PlanState.Error(e.message ?: "계획 생성에 실패했습니다")
            }
        }
    }

    /**
     * 계획 전체 조회
     */
    fun getPlans(
        page: Int? = null,
        take: Int? = null,
        order: String? = null,
        topic: String? = null
    ) {
        viewModelScope.launch {
            _planState.value = PlanState.Loading
            Log.d("PlanViewModel", "계획 전체 조회 시작")

            val result = getPlansUseCase(page, take, order, topic)

            result.onSuccess { plans ->
                Log.d("PlanViewModel", "✅ 계획 ${plans.size}개 조회 성공")

                _plans.value = plans
                _planState.value = PlanState.Success
            }.onFailure { e ->
                Log.e("PlanViewModel", "❌ 계획 조회 실패", e)
                _planState.value = PlanState.Error(e.message ?: "계획 조회에 실패했습니다")
            }
        }
    }

    /**
     * 계획 단건 조회
     */
    fun getPlan(planId: Int) {
        viewModelScope.launch {
            _planState.value = PlanState.Loading
            Log.d("PlanViewModel", "계획 단건 조회 시작: planId=$planId")

            val result = getPlanUseCase(planId)

            result.onSuccess { plan ->
                Log.d("PlanViewModel", "✅ 계획 조회 성공: ${plan.topic}")
                _planState.value = PlanState.Success
            }.onFailure { e ->
                Log.e("PlanViewModel", "❌ 계획 조회 실패", e)
                _planState.value = PlanState.Error(e.message ?: "계획 조회에 실패했습니다")
            }
        }
    }

    /**
     * 계획 수정
     */
    fun updatePlan(
        planId: Int,
        topic: String? = null,
        location: String? = null,
        time: String? = null
    ) {
        viewModelScope.launch {
            _planState.value = PlanState.Loading
            Log.d("PlanViewModel", "계획 수정 시작: planId=$planId")

            val request = UpdatePlanRequest(topic = topic, location = location, time = time)
            val result = updatePlanUseCase(planId, request)

            result.onSuccess { updatedPlan ->
                _plans.value = _plans.value.map { plan ->
                    if(plan.id == planId) updatedPlan else plan
                }
                _planState.value = PlanState.Success
            }.onFailure { e ->
                Log.e("PlanViewModel", "❌ 계획 수정 실패", e)
                _planState.value = PlanState.Error(e.message ?: "계획 수정에 실패했습니다")
            }
        }
    }

    /**
     * 계획 삭제
     */
    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            _planState.value = PlanState.Loading
            Log.d("PlanViewModel", "계획 삭제 시작: planId=$planId")

            val result = deletePlanUseCase(planId)

            result.onSuccess { deletedId ->
                Log.d("PlanViewModel", "✅ 계획 삭제 성공: deletedId=$deletedId")
                _plans.value = _plans.value.filter { it.id != deletedId}
                _planState.value = PlanState.Success
            }.onFailure { e ->
                Log.e("PlanViewModel", "❌ 계획 삭제 실패", e)
                _planState.value = PlanState.Error(e.message ?: "계획 삭제에 실패했습니다")
            }
        }
    }

    /**
     * State 초기화
     */
    fun resetState() {
        _planState.value = PlanState.Idle
    }
}

/**
 * 계획 관련 상태
 */
sealed class PlanState {
    data object Idle : PlanState()
    data object Loading : PlanState()
    data object Success : PlanState()
    data class Error(val message: String) : PlanState()
}