// core/network/RetrofitClient.kt
package com.example.dulit.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://192.168.45.42:3000/";
    // private const val BASE_URL = "http://10.0.2.2:3000/" // 에뮬레이터
    // 실제 기기: "http://192.168.x.x:3000/"
    // 프로덕션: "https://api.dulit.com/"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    // 제네릭 함수로 모든 API 생성 가능
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}