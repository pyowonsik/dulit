// core/network/LoggingInterceptor.kt
package com.example.dulit.core.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return loggingInterceptor.intercept(chain)
    }
}