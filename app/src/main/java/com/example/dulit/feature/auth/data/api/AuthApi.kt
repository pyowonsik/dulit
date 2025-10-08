package com.example.dulit.feature.auth.data.api

import com.example.dulit.feature.auth.data.model.KakaoLoginRequest
import com.example.dulit.feature.auth.data.model.KakaoLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response


interface AuthApi {
    @POST("auth/kakao/callback")
    suspend fun kakaoLogin(
        @Body request: KakaoLoginRequest
    ): Response<KakaoLoginResponse>
}
