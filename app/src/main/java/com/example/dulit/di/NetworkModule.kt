package com.example.dulit.di

import com.example.dulit.core.network.AuthInterceptor
import com.example.dulit.core.network.LoggingInterceptor
import com.example.dulit.core.network.TokenAuthenticator
import com.example.dulit.feature.auth.data.api.AuthApi
import com.example.dulit.feature.calendar.data.api.CalendarApi
import com.example.dulit.feature.couple.data.api.CoupleApi
import com.example.dulit.feature.home.data.api.AnniversaryApi
import com.example.dulit.feature.home.data.api.PlanApi
import com.example.dulit.feature.post.data.api.PostApi
import com.example.dulit.feature.user.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://192.168.45.42:3000/"

    // ⭐토큰 갱신 전용 클라이언트 (Authenticator 제외) ->  Header에 AcceesToken 추가 X
    @Provides
    @Singleton
    @Named("AuthOkHttpClient")
    fun provideAuthOkHttpClient(
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ⭐AuthApi는 토큰 갱신 전용 클라이언트 사용
    @Provides
    @Singleton
    fun provideAuthApi(
        @Named("AuthOkHttpClient") okHttpClient: OkHttpClient
    ): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    // ⭐ 일반 API용 클라이언트 (Authenticator 포함) -> Header에 AcceesToken 추가
    @Provides
    @Singleton
    @Named("MainOkHttpClient")
    fun provideMainOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)  // ⭐ Authenticator 추가
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ⭐ UserApi는 일반 클라이언트 사용
    @Provides
    @Singleton
    fun provideUserApi(
        @Named("MainOkHttpClient") okHttpClient: OkHttpClient
    ): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    // ⭐ CoupleApi도 일반 클라이언트 사용
    @Provides
    @Singleton
    fun provideCoupleApi(
        @Named("MainOkHttpClient") okHttpClient: OkHttpClient
    ): CoupleApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoupleApi::class.java)
    }

    // ⭐ CalendarApi도 일반 클라이언트 사용
    @Provides
    @Singleton
    fun provideCalendarApi(
        @Named("MainOkHttpClient") okHttpClient: OkHttpClient
    ): CalendarApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CalendarApi::class.java)
    }

    // ⭐ PostApi도 일반 클라이언트 사용
    @Provides
    @Singleton
    fun providePostApi(
        @Named("MainOkHttpClient") okHttpClient: OkHttpClient
    ): PostApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }

    // ⭐ AnniversaryApi도 일반 클라이언트 사용
    @Provides
    @Singleton
    fun provideAnniversaryApi(
        @Named("MainOkHttpClient") okHttpClient: OkHttpClient
    ): AnniversaryApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnniversaryApi::class.java)
    }

    // ⭐ PlanApi도 일반 클라이언트 사용
    @Provides
    @Singleton
    fun providePlanApi(
        @Named("MainOkHttpClient") okHttpClient: OkHttpClient
    ): PlanApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlanApi::class.java)
    }
}