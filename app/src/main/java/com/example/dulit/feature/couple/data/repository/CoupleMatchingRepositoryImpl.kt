// feature/couple/data/repository/CoupleMatchingRepositoryImpl.kt
package com.example.dulit.feature.couple.data.repository

import com.example.dulit.feature.couple.data.remote.CoupleMatchingSocketClient
import com.example.dulit.feature.couple.data.remote.MatchingSocketEvent
import com.example.dulit.feature.couple.domain.repository.CoupleMatchingRepository
import com.example.dulit.feature.couple.domain.repository.MatchingSocketState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoupleMatchingRepositoryImpl @Inject constructor(
    private val socketClient: CoupleMatchingSocketClient
) : CoupleMatchingRepository {

    override fun connectSocket(userId: String): Flow<MatchingSocketState> {
        return socketClient.connect(userId).map { event ->
            when (event) {
                is MatchingSocketEvent.Connected -> MatchingSocketState.Connected
                is MatchingSocketEvent.Disconnected -> MatchingSocketState.Disconnected
                is MatchingSocketEvent.Matched -> MatchingSocketState.Matched(event.message)
                is MatchingSocketEvent.Error -> MatchingSocketState.Error(event.message)
            }
        }
    }

    override fun disconnectSocket() {
        socketClient.disconnect()
    }

    override fun isConnected(): Boolean {
        return socketClient.isConnected()
    }
}