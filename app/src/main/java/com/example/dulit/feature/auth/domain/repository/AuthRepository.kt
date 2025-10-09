package com.example.dulit.feature.auth.domain.repository

import com.example.dulit.feature.auth.domain.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.model.KakaoLoginRquest
import com.example.dulit.feature.auth.domain.model.RotateAccessTokenResponse

interface AuthRepository {
    suspend fun kakaoLogin(kakaoToken: KakaoLoginRquest): Result<KakaoLoginResponse>
    suspend fun rotateAccessToken() : Result<RotateAccessTokenResponse>
}

