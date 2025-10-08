// feature/user/presentation/ConnectCoupleViewModel.kt
package com.example.dulit.feature.user.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.dulit.feature.user.data.model.ConnectCoupleResponse
import com.example.dulit.feature.user.domain.usecase.ConnectCoupleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectCoupleViewModel @Inject constructor(
    private val connectCoupleUseCase: ConnectCoupleUseCase
) : ViewModel() {

    private val _connectState = MutableStateFlow<ConnectCoupleState>(ConnectCoupleState.Idle)
    val connectState: StateFlow<ConnectCoupleState> = _connectState.asStateFlow()

    fun connectCouple(partnerId: String) {
        viewModelScope.launch {
            _connectState.value = ConnectCoupleState.Loading
            Log.d("ConnectCoupleViewModel", "🔗 커플 연결 시작: $partnerId")

            connectCoupleUseCase(partnerId)
                .onSuccess { response ->
                    Log.d("ConnectCoupleViewModel", "✅ 연결 성공")
                    _connectState.value = ConnectCoupleState.Success(response)
                }
                .onFailure { error ->
                    Log.e("ConnectCoupleViewModel", "❌ 연결 실패", error)
                    _connectState.value = ConnectCoupleState.Error(error.message ?: "연결 실패")
                }
        }
    }

    fun resetState() {
        _connectState.value = ConnectCoupleState.Idle
    }
}

sealed class ConnectCoupleState {
    object Idle : ConnectCoupleState()
    object Loading : ConnectCoupleState()
    data class Success(val response: Boolean) : ConnectCoupleState()
    data class Error(val message: String) : ConnectCoupleState()
}