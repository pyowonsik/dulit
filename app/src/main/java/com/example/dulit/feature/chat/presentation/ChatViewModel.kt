// feature/chat/presentation/ChatViewModel.kt
package com.example.dulit.feature.chat.presentation

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
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private var socket: Socket? = null

    private val _chatState = MutableStateFlow<ChatState>(ChatState.Idle)
    val chatState: StateFlow<ChatState> = _chatState

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private companion object {
        const val SOCKET_URL = "http://192.168.0.32:3000"
    }

    fun connectChatSocket() {
        Log.d("ChatViewModel", "🔌 채팅 소켓 연결 시작...")

        viewModelScope.launch {
            try {
                val accessToken = tokenStorage.getAccessToken() ?: ""
                Log.d("ChatViewModel", "Token: ${accessToken.take(20)}...")
                Log.d("ChatViewModel", "🌐 연결 URL: $SOCKET_URL/chat")

                val options = IO.Options().apply {
                    query = "token=Bearer $accessToken"
                    transports = arrayOf("polling", "websocket")
                    reconnection = true
                    reconnectionAttempts = 5
                    reconnectionDelay = 2000
                    timeout = 20000
                    forceNew = true
                }

                socket = IO.socket("$SOCKET_URL/chat", options)

                // 연결 성공
                socket?.on(Socket.EVENT_CONNECT) {
                    Log.i("ChatViewModel", "✅ 채팅 소켓 연결 성공!")
                    Log.i("ChatViewModel", "Socket ID: ${socket?.id()}")
                    _chatState.value = ChatState.Connected
                }

                // 연결 실패
                socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                    val error = args.getOrNull(0)
                    Log.e("ChatViewModel", "❌ 연결 실패: $error")
                    _chatState.value = ChatState.Error(error?.toString() ?: "연결 실패")
                }

                // 연결 해제
                socket?.on(Socket.EVENT_DISCONNECT) { args ->
                    Log.i("ChatViewModel", "❌ 연결 해제: ${args.joinToString()}")
                    _chatState.value = ChatState.Disconnected
                }

                // 서버 에러
                socket?.on("error") { args ->
                    val errorData = args.getOrNull(0) as? JSONObject
                    val message = errorData?.getString("message") ?: "에러 발생"
                    Log.e("ChatViewModel", "❌ 서버 에러: $message")
                    _chatState.value = ChatState.Error(message)
                }
//
//                // 메시지 수신
//                socket?.on("sendMessage") { args ->
//                    val data = args.getOrNull(0) as? JSONObject
//                    Log.i("ChatViewModel", "📩 메시지 수신: $data")
//
//                    data?.let {
//                        try {
//                            val authorObj = it.optJSONObject("author")
//                            val message = ChatMessage(
//                                id = it.optInt("id"),
//                                message = it.optString("message"),
//                                authorId = authorObj?.optInt("id") ?: 0,
//                                authorName = authorObj?.optString("name") ?: "알 수 없음",
//                                createdAt = it.optString("createdAt")
//                            )
//
//                            _messages.value = _messages.value + message
//                            Log.d("ChatViewModel", "메시지 추가됨: ${message.message}")
//                        } catch (e: Exception) {
//                            Log.e("ChatViewModel", "메시지 파싱 실패", e)
//                        }
//                    }
//                }

                socket?.connect()
                Log.d("ChatViewModel", "채팅 소켓 연결 시도 중...")

            } catch (e: Exception) {
                Log.e("ChatViewModel", "❌ 예외 발생", e)
                _chatState.value = ChatState.Error(e.message ?: "연결 실패")
            }
        }
    }

    fun sendMessage(message: String) {
        Log.d("ChatViewModel", "📤 메시지 전송: $message")

        try {
            val data = JSONObject().apply {
                put("message", message)
            }

            socket?.emit("sendMessage", data)
            Log.d("ChatViewModel", "메시지 emit 완료")
        } catch (e: Exception) {
            Log.e("ChatViewModel", "메시지 전송 실패", e)
        }
    }

    fun disconnectChatSocket() {
        if (socket == null) {
            Log.d("ChatViewModel", "이미 해제됨 - 스킵")
            return
        }

        Log.d("ChatViewModel", "🔌 채팅 소켓 연결 해제 중...")
        socket?.disconnect()
        socket?.off()
        socket = null
        _chatState.value = ChatState.Disconnected
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ChatViewModel", "🔌 ViewModel 정리 - 채팅 소켓 자동 해제")
        disconnectChatSocket()
    }
}

//// 채팅 메시지 데이터 클래스
//data class ChatMessage(
//    val id: Int,
//    val message: String,
//    val authorId: Int,
//    val authorName: String,
//    val createdAt: String
//)

// 채팅 상태
sealed class ChatState {
    object Idle : ChatState()
    object Connected : ChatState()
    object Disconnected : ChatState()
    data class Error(val message: String) : ChatState()
}