package com.example.dulit.feature.auth.data.model

import com.example.dulit.feature.user.data.model.UserDto

data class KakaoLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDto,
    val isCouple : Boolean = false
)
