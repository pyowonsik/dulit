// feature/couple/presentation/CoupleMatchingViewModel.kt
package com.example.dulit.feature.couple.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulit.feature.couple.domain.repository.MatchingSocketState
import com.example.dulit.feature.couple.domain.usecase.ConnectMatchingSocketUseCase
import com.example.dulit.feature.couple.domain.usecase.DisconnectMatchingSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoupleMatchingViewModel @Inject constructor(
    private val connectMatchingSocketUseCase: ConnectMatchingSocketUseCase,
    private val disconnectMatchingSocketUseCase: DisconnectMatchingSocketUseCase
) : ViewModel() {

    private val _matchingState = MutableStateFlow<MatchingSocketState>(MatchingSocketState.Idle)
    val matchingState: StateFlow<MatchingSocketState> = _matchingState

    fun connectSocket(userId: String) {
        Log.d("CoupleMatchingViewModel", "🔌 매칭 소켓 연결 시작... userId=$userId")

        viewModelScope.launch {
            connectMatchingSocketUseCase(userId).collect { state ->
                Log.d("CoupleMatchingViewModel", "상태 변경: $state")
                _matchingState.value = state
            }
        }
    }

    fun disconnectSocket() {
        if (_matchingState.value is MatchingSocketState.Disconnected) {
            Log.d("CoupleMatchingViewModel", "⏭️ 이미 해제됨 - 스킵")
            return
        }

        Log.d("CoupleMatchingViewModel", "🔌 매칭 소켓 연결 해제")

        disconnectMatchingSocketUseCase()
        _matchingState.value = MatchingSocketState.Disconnected
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CoupleMatchingViewModel", "🔌 ViewModel 정리 - 소켓 자동 해제")
        disconnectSocket()
    }
}