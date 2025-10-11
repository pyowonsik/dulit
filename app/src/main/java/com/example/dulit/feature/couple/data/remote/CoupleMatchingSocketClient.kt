// feature/couple/data/remote/CoupleMatchingSocketClient.kt
package com.example.dulit.feature.couple.data.remote

import android.util.Log
import com.example.dulit.core.local.TokenStorage
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoupleMatchingSocketClient @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    private var socket: Socket? = null

    companion object {
        private const val SOCKET_URL = "http://192.168.0.32:3000"
    }

    fun connect(userId: String): Flow<MatchingSocketEvent> = callbackFlow {
        try {
            val accessToken = tokenStorage.getAccessToken() ?: ""
            Log.d("CoupleMatchingSocket", "🔌 소켓 연결 시작... userId=$userId")
            Log.d("CoupleMatchingSocket", "Token: ${accessToken.take(20)}...")

            val options = IO.Options().apply {
                query = "userId=$userId&token=Bearer $accessToken"
                transports = arrayOf("polling", "websocket")
                reconnection = true
                reconnectionAttempts = 5
                reconnectionDelay = 2000
                timeout = 20000
                forceNew = true
            }

            socket = IO.socket("$SOCKET_URL/notification", options)

            // 연결 성공
            socket?.on(Socket.EVENT_CONNECT) {
                Log.i("CoupleMatchingSocket", "✅ 소켓 연결 성공!")
                Log.i("CoupleMatchingSocket", "Socket ID: ${socket?.id()}")
                trySend(MatchingSocketEvent.Connected)
            }

            // 연결 실패
            socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                val error = args.getOrNull(0)
                Log.e("CoupleMatchingSocket", "❌ 연결 실패: $error")
                trySend(MatchingSocketEvent.Error(error?.toString() ?: "연결 실패"))
            }

            // 연결 해제
            socket?.on(Socket.EVENT_DISCONNECT) { args ->
                Log.i("CoupleMatchingSocket", "❌ 연결 해제: ${args.joinToString()}")
                trySend(MatchingSocketEvent.Disconnected)
            }

            // 매칭 알림
            socket?.on("matchedNotification") { args ->
                val message = args.getOrNull(0) as? String
                Log.i("CoupleMatchingSocket", "📩 매칭 알림 수신: $message")
                trySend(MatchingSocketEvent.Matched(message ?: "커플 연결 완료!"))
            }

            socket?.connect()
            Log.d("CoupleMatchingSocket", "소켓 연결 시도 중...")

        } catch (e: Exception) {
            Log.e("CoupleMatchingSocket", "❌ 예외 발생", e)
            trySend(MatchingSocketEvent.Error(e.message ?: "연결 실패"))
        }

        awaitClose {
            Log.d("CoupleMatchingSocket", "Flow 종료 - 소켓 해제")
            disconnect()
        }
    }

    fun disconnect() {
        if (socket == null) {
            Log.d("CoupleMatchingSocket", "이미 해제됨")
            return
        }

        Log.d("CoupleMatchingSocket", "🔌 소켓 해제")
        socket?.disconnect()
        socket?.off()
        socket = null
    }

    fun isConnected(): Boolean = socket?.connected() == true
}

sealed class MatchingSocketEvent {
    object Connected : MatchingSocketEvent()
    object Disconnected : MatchingSocketEvent()
    data class Matched(val message: String) : MatchingSocketEvent()
    data class Error(val message: String) : MatchingSocketEvent()
}