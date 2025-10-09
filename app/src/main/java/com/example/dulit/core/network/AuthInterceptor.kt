// core/network/AuthInterceptor.kt
package com.example.dulit.core.network

import com.example.dulit.core.local.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val path = originalRequest.url.encodedPath

//        // /auth로 시작하는 요청은 토큰 불필요
//        val token = if (path.startsWith("/auth")) {
//            null
//        } else {
//            tokenStorage.getAccessToken()
//        }
        val token = tokenStorage.getAccessToken()

        val newRequest = if (token != null) {
            originalRequest.newBuilder().addHeader("Authorization", "Bearer $token").build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}