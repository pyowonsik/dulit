package com.example.dulit.feature.user.domain.repository

import com.example.dulit.feature.user.domain.model.User

interface UserRepository {
    suspend fun kakaoLogin(kakaoToken: String): Result<User>
}