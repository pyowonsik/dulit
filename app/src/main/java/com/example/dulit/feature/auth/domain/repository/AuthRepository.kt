package com.example.dulit.feature.auth.domain.repository

import com.example.dulit.feature.auth.data.model.KakaoLoginResponse

interface AuthRepository {
    suspend fun kakaoLogin(kakaoToken: String): Result<KakaoLoginResponse>
}

