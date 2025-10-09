package com.example.dulit.feature.couple.domain.repository

import kotlinx.coroutines.flow.Flow

interface CoupleMatchingRepository {
    fun connectSocket(userId: String): Flow<MatchingSocketState>
    fun disconnectSocket()
    fun isConnected(): Boolean
}

sealed class MatchingSocketState {
    object Idle : MatchingSocketState()
    object Connected : MatchingSocketState()
    object Disconnected : MatchingSocketState()
    data class Matched(val message: String) : MatchingSocketState()
    data class Error(val message: String) : MatchingSocketState()
}