// feature/user/presentation/ConnectViewModel.kt
package com.example.dulit.feature.user.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulit.core.local.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private var socket: Socket? = null

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Idle)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    // 👇 NetworkModule과 동일한 IP 사용
    private companion object {
        const val SOCKET_URL = "http://192.168.45.42:3000"  // 👈 여기 수정!
    }

    fun connectSocket(userId: String) {
        Log.d("ConnectViewModel", "🔌 소켓 연결 시작... userId=$userId")

        viewModelScope.launch {
            try {
                val accessToken = tokenStorage.getAccessToken() ?: ""
                Log.d("ConnectViewModel", "Token: ${accessToken.take(20)}...")
                Log.d("ConnectViewModel", "🌐 연결 URL: $SOCKET_URL/notification")

                val options = IO.Options().apply {
                    query = "userId=$userId&token=Bearer $accessToken"
                    transports = arrayOf("polling", "websocket")  // polling 먼저
                    reconnection = true
                    reconnectionAttempts = 5
                    reconnectionDelay = 2000
                    timeout = 20000
                    forceNew = true
                }

                socket = IO.socket("$SOCKET_URL/notification", options)

                socket?.on(Socket.EVENT_CONNECT) {
                    Log.i("ConnectViewModel", "✅ 소켓 연결 성공!")
                    Log.i("ConnectViewModel", "Socket ID: ${socket?.id()}")
                    _connectionState.value = ConnectionState.Connected
                }

                socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                    val error = args.getOrNull(0)
                    Log.e("ConnectViewModel", "❌ 연결 실패: $error")
                    _connectionState.value = ConnectionState.Error(error?.toString() ?: "연결 실패")
                }

                socket?.on(Socket.EVENT_DISCONNECT) { args ->
                    Log.i("ConnectViewModel", "❌ 연결 해제: ${args.joinToString()}")
                    _connectionState.value = ConnectionState.Disconnected
                }

                socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                    Log.e("ConnectViewModel", "❌ 소켓 에러: ${args.joinToString()}")
                }

                socket?.on("matchedNotification") { args ->
                    val message = args.getOrNull(0) as? String
                    Log.i("ConnectViewModel", "📩 알림 수신: $message")
                    _connectionState.value = ConnectionState.Matched(message ?: "연결 완료!")
                }

                socket?.connect()
                Log.d("ConnectViewModel", "소켓 연결 시도 중...")

            } catch (e: Exception) {
                Log.e("ConnectViewModel", "❌ 예외 발생", e)
                _connectionState.value = ConnectionState.Error(e.message ?: "연결 실패")
            }
        }
    }

    fun disconnectSocket() {
        Log.d("ConnectViewModel", "🔌 소켓 연결 해제 중...")
        socket?.disconnect()
        socket?.off()
        socket = null
        _connectionState.value = ConnectionState.Disconnected
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ConnectViewModel", "🔌 ViewModel 정리 - 소켓 자동 해제")
        disconnectSocket()
    }
}

sealed class ConnectionState {
    object Idle : ConnectionState()
    object Connected : ConnectionState()
    object Disconnected : ConnectionState()
    data class Matched(val message: String) : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}