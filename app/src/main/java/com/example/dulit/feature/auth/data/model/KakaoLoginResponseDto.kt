package com.example.dulit.feature.auth.data.model

import com.example.dulit.feature.auth.domain.model.KakaoLoginResponse
import com.example.dulit.feature.user.domain.model.User

data class KakaoLoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
    val isCouple : Boolean = false
)

fun KakaoLoginResponseDto.toDomain(): KakaoLoginResponse {
    return KakaoLoginResponse(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user,
        isCouple = isCouple
    )
}