package com.example.dulit.feature.user.data.model

data class KakaoLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto
)
