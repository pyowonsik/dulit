package com.example.dulit.feature.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.CreateAnniversaryRequest
import com.example.dulit.feature.home.domain.model.UpdateAnniversaryRequest
import com.example.dulit.feature.home.domain.usecase.CreateAnniversaryUseCase
import com.example.dulit.feature.home.domain.usecase.DeleteAnniversaryUseCase
import com.example.dulit.feature.home.domain.usecase.GetAnniversariesUseCase
import com.example.dulit.feature.home.domain.usecase.GetAnniversaryUseCase
import com.example.dulit.feature.home.domain.usecase.UpdateAnniversaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnniversaryViewModel @Inject constructor(
    private val createAnniversaryUseCase: CreateAnniversaryUseCase,
    private val getAnniversariesUseCase: GetAnniversariesUseCase,
    private val getAnniversaryUseCase: GetAnniversaryUseCase,
    private val updateAnniversaryUseCase: UpdateAnniversaryUseCase,
    private val deleteAnniversaryUseCase: DeleteAnniversaryUseCase
) : ViewModel() {

    private val _anniversaries = MutableStateFlow<List<Anniversary>>(emptyList())
    val anniversaries: StateFlow<List<Anniversary>> = _anniversaries.asStateFlow()
    
    private val _anniversaryState = MutableStateFlow<AnniversaryState>(AnniversaryState.Idle)
    val anniversaryState: StateFlow<AnniversaryState> = _anniversaryState.asStateFlow()

    /**
     * 기념일 생성
     */
    fun createAnniversary(
        title: String,
        date: String
    ) {
        viewModelScope.launch {
            _anniversaryState.value = AnniversaryState.Loading
            Log.d("AnniversaryViewModel", "기념일 생성 시작: title=$title, date=$date")

            val request = CreateAnniversaryRequest(title = title, date = date)
            val result = createAnniversaryUseCase(request)

            result.onSuccess { anniversary ->
                Log.d("AnniversaryViewModel", "✅ 기념일 생성 성공: ${anniversary.title}")
                // ⭐ 리스트에 추가
                _anniversaries.value = _anniversaries.value + anniversary
                _anniversaryState.value = AnniversaryState.Success
            }.onFailure { e ->
                Log.e("AnniversaryViewModel", "❌ 기념일 생성 실패", e)
                _anniversaryState.value = AnniversaryState.Error(e.message ?: "기념일 생성에 실패했습니다")
            }
        }
    }

    /**
     * 기념일 전체 조회 (백엔드에서 날짜순 정렬)
     */
    fun getAnniversaries(
        title: String? = null
    ) {
        viewModelScope.launch {
            _anniversaryState.value = AnniversaryState.Loading
            Log.d("AnniversaryViewModel", "기념일 전체 조회 시작${if (title != null) " (title=$title)" else ""}")

            val result = getAnniversariesUseCase(title)

            result.onSuccess { fetchedAnniversaries ->
                Log.d("AnniversaryViewModel", "✅ 기념일 ${fetchedAnniversaries.size}개 조회 성공")
                // ⭐ 리스트 전체 교체
                _anniversaries.value = fetchedAnniversaries
                _anniversaryState.value = AnniversaryState.Success
            }.onFailure { e ->
                Log.e("AnniversaryViewModel", "❌ 기념일 조회 실패", e)
                _anniversaryState.value = AnniversaryState.Error(e.message ?: "기념일 조회에 실패했습니다")
            }
        }
    }

    /**
     * 기념일 단건 조회 (리스트에는 영향 없음)
     */
    fun getAnniversary(anniversaryId: Int) {
        viewModelScope.launch {
            _anniversaryState.value = AnniversaryState.Loading
            Log.d("AnniversaryViewModel", "기념일 단건 조회 시작: anniversaryId=$anniversaryId")

            val result = getAnniversaryUseCase(anniversaryId)

            result.onSuccess { anniversary ->
                Log.d("AnniversaryViewModel", "✅ 기념일 조회 성공: ${anniversary.title}")
                // ⭐ 단건 조회는 리스트 수정 안 함 (필요시 UI에서 처리)
                _anniversaryState.value = AnniversaryState.Success
            }.onFailure { e ->
                Log.e("AnniversaryViewModel", "❌ 기념일 조회 실패", e)
                _anniversaryState.value = AnniversaryState.Error(e.message ?: "기념일 조회에 실패했습니다")
            }
        }
    }

    /**
     * 기념일 수정
     */
    fun updateAnniversary(
        anniversaryId: Int,
        title: String? = null,
        date: String? = null
    ) {
        viewModelScope.launch {
            _anniversaryState.value = AnniversaryState.Loading
            Log.d("AnniversaryViewModel", "기념일 수정 시작: anniversaryId=$anniversaryId")

            val request = UpdateAnniversaryRequest(title = title, date = date)
            val result = updateAnniversaryUseCase(anniversaryId, request)

            result.onSuccess { updatedAnniversary ->
                Log.d("AnniversaryViewModel", "✅ 기념일 수정 성공: ${updatedAnniversary.title}")
                // ⭐ 리스트에서 해당 항목 업데이트
                _anniversaries.value = _anniversaries.value.map { anniversary ->
                    if (anniversary.id == anniversaryId) updatedAnniversary else anniversary
                }
                _anniversaryState.value = AnniversaryState.Success
            }.onFailure { e ->
                Log.e("AnniversaryViewModel", "❌ 기념일 수정 실패", e)
                _anniversaryState.value = AnniversaryState.Error(e.message ?: "기념일 수정에 실패했습니다")
            }
        }
    }

    /**
     * 기념일 삭제
     */
    fun deleteAnniversary(anniversaryId: Int) {
        viewModelScope.launch {
            _anniversaryState.value = AnniversaryState.Loading
            Log.d("AnniversaryViewModel", "기념일 삭제 시작: anniversaryId=$anniversaryId")

            val result = deleteAnniversaryUseCase(anniversaryId)

            result.onSuccess { deletedId ->
                Log.d("AnniversaryViewModel", "✅ 기념일 삭제 성공: deletedId=$deletedId")
                // ⭐ 리스트에서 제거
                _anniversaries.value = _anniversaries.value.filter { it.id != deletedId }
                _anniversaryState.value = AnniversaryState.Success
            }.onFailure { e ->
                Log.e("AnniversaryViewModel", "❌ 기념일 삭제 실패", e)
                _anniversaryState.value = AnniversaryState.Error(e.message ?: "기념일 삭제에 실패했습니다")
            }
        }
    }

    /**
     * UI State 초기화 (데이터는 유지)
     */
    fun resetUiState() {
        _anniversaryState.value = AnniversaryState.Idle
    }
}

/**
 * 기념일 UI 상태 (데이터는 별도 관리)
 */
sealed class AnniversaryState {
    data object Idle : AnniversaryState()
    data object Loading : AnniversaryState()
    data object Success : AnniversaryState()
    data class Error(val message: String) : AnniversaryState()
}
