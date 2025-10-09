package com.example.dulit.feature.auth.data.model

import com.example.dulit.feature.auth.domain.model.KakaoLoginRquest
import com.google.gson.annotations.SerializedName

data class KakaoLoginRequestDto(
    @SerializedName("kakaoAccessToken")
    val kakaoAccessToken: String
)

// 👇 DTO → Domain 변환 함수
fun KakaoLoginRequestDto.toDomain(): KakaoLoginRquest {
    return KakaoLoginRquest(
        kakaoAccessToken = kakaoAccessToken
    );
}