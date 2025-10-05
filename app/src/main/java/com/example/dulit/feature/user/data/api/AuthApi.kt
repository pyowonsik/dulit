package com.example.dulit.feature.user.data.api

import com.example.dulit.feature.user.data.model.KakaoLoginRequest
import com.example.dulit.feature.user.data.model.KakaoLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response


interface AuthApi {
    @POST("auth/kakao/callback")
    suspend fun kakaoLogin(
        @Body request: KakaoLoginRequest
    ): Response<KakaoLoginResponse>
}
