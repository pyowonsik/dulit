// di/NetworkModule.kt
package com.example.dulit.di

import android.content.Context
import android.util.Log
import com.example.dulit.core.local.TokenStorage
import com.example.dulit.feature.user.data.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://192.168.45.42:3000/"

    @Provides
    @Singleton
    fun provideTokenStorage(
        @ApplicationContext context: Context
    ): TokenStorage {
        return TokenStorage(context)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //    @Provides
    //    @Singleton
    //    fun provideOkHttpClient(
    //        loggingInterceptor: HttpLoggingInterceptor
    //    ): OkHttpClient {
    //        return OkHttpClient.Builder()
    //            .addInterceptor(loggingInterceptor)  // ⭐ 로깅만 유지
    //            .connectTimeout(30, TimeUnit.SECONDS)
    //            .readTimeout(30, TimeUnit.SECONDS)
    //            .writeTimeout(30, TimeUnit.SECONDS)
    //            .build()
    //    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenStorage: TokenStorage, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = tokenStorage.getAccessToken()

                // ⭐ /auth로 시작하는 요청은 JWT 추가 안 함
                val isAuthRequest = originalRequest.url.encodedPath.startsWith("/auth")

                val newRequest = if (token != null && !isAuthRequest) {
                    originalRequest.newBuilder().addHeader("Authorization", "Bearer $token").build()
                } else {
                    originalRequest
                }

                chain.proceed(newRequest)
            }.addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}