package com.example.dulit.di.couple

import com.example.dulit.feature.couple.data.api.CoupleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoupleApiModule {

    @Provides
    @Singleton
    fun provideCoupleApi(retrofit: Retrofit): CoupleApi {
        return retrofit.create(CoupleApi::class.java)
    }
}