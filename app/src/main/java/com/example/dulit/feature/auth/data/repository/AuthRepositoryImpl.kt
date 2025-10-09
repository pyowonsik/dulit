// feature/auth/data/repository/AuthRepositoryImpl.kt
package com.example.dulit.feature.auth.data.repository

import android.util.Log
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.auth.data.api.AuthApi
import com.example.dulit.feature.auth.data.model.KakaoLoginRequestDto
import com.example.dulit.feature.auth.data.model.toDomain
import com.example.dulit.feature.auth.domain.model.KakaoLoginResponse
import com.example.dulit.feature.auth.domain.model.KakaoLoginRquest
import com.example.dulit.feature.auth.domain.model.RotateAccessTokenResponse
import com.example.dulit.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun kakaoLogin(
        kakaoToken: KakaoLoginRquest
    ): Result<KakaoLoginResponse> = runCatching {
        val response = authApi.kakaoLogin(
            KakaoLoginRequestDto(kakaoAccessToken = kakaoToken.kakaoAccessToken)
        )

        // RAW JSON 전체 로그
        Log.d("AuthRepositoryImpl", "=== RAW RESPONSE ===")
        Log.d("AuthRepositoryImpl", "Status Code: ${response.code()}")
        Log.d("AuthRepositoryImpl", "Response Body: ${response.body()}")
        Log.d("AuthRepositoryImpl", "Raw JSON: ${response.raw()}")

        if (!response.isSuccessful || response.body() == null) {
            Log.e("AuthRepositoryImpl", "로그인 실패: ${response.code()}")
            throw Exception("로그인 실패: ${response.code()}")
        }

        val body = response.body()!!

        Log.i("AuthRepositoryImpl", "로그인 성공: ${body.user.name}")
        Log.i("AuthRepositoryImpl", "JWT: ${body.accessToken}")

        // JWT 토큰 + socialId 저장
        tokenStorage.saveAccessToken(body.accessToken)
        tokenStorage.saveRefreshToken(body.refreshToken)
        tokenStorage.saveSocialId(body.user.socialId)

        Log.d("AuthRepositoryImpl [User]", body.toString())

        // ⭐ 마지막 표현식이 자동으로 Result.success()
        body.toDomain()
    }.onFailure { e ->
        Log.e("AuthRepositoryImpl", "카카오 로그인 에러", e)
    }

    override suspend fun rotateAccessToken(): Result<RotateAccessTokenResponse> = runCatching {
        Log.d("AuthRepositoryImpl", "토큰 갱신 시작")

        // 리프레시 토큰 가져오기
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrEmpty()) {
            Log.e("AuthRepositoryImpl", "리프레시 토큰이 없습니다")
            throw Exception("리프레시 토큰이 없습니다")
        }

        // "Bearer <리프레시토큰>" 형식으로 API 호출
        val responseDto = authApi.rotateAccessToken("Bearer $refreshToken")

        // DTO → Domain Model 변환
        val domainModel = responseDto.toDomain()

        // 새 액세스 토큰 저장
        tokenStorage.saveAccessToken(domainModel.accessToken)

        Log.d("AuthRepositoryImpl", "토큰 갱신 성공: ${domainModel.accessToken.take(20)}...")

        // ⭐ 마지막 표현식이 자동으로 Result.success()
        domainModel
    }.onFailure { e ->
        Log.e("AuthRepositoryImpl", "토큰 갱신 실패", e)
    }
}