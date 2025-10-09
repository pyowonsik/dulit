package com.example.dulit.feature.auth.data.model

import com.example.dulit.feature.auth.domain.model.RotateAccessTokenResponse

data class RotateAccessTokenResponseDto(
    val accessToken: String
)


// 👇 DTO → Domain 변환 함수
fun RotateAccessTokenResponseDto.toDomain(): RotateAccessTokenResponse {
    return RotateAccessTokenResponse(
       accessToken = accessToken
    )
}