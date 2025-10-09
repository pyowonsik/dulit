package com.example.dulit.feature.auth.data.api

import com.example.dulit.feature.auth.data.model.KakaoLoginRequestDto
import com.example.dulit.feature.auth.data.model.KakaoLoginResponseDto
import com.example.dulit.feature.auth.data.model.RotateAccessTokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response


interface AuthApi {
    @POST("auth/kakao/callback")
    suspend fun kakaoLogin(
        @Body request: KakaoLoginRequestDto
    ): Response<KakaoLoginResponseDto>

    @POST("auth/token/access")
    suspend fun rotateAccessToken(): RotateAccessTokenResponseDto
}
