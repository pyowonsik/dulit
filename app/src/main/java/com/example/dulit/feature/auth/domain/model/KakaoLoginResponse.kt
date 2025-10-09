package com.example.dulit.feature.auth.domain.model

import com.example.dulit.feature.user.domain.model.User

data class KakaoLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
    val isCouple : Boolean = false
)