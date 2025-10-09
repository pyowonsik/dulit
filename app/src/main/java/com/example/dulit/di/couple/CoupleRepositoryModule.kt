// core/di/couple/CoupleRepositoryModule.kt
package com.example.dulit.di.couple

import com.example.dulit.feature.couple.data.repository.CoupleMatchingRepositoryImpl
import com.example.dulit.feature.couple.data.repository.CoupleRepositoryImpl
import com.example.dulit.feature.couple.domain.repository.CoupleMatchingRepository
import com.example.dulit.feature.couple.domain.repository.CoupleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoupleRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoupleRepository(
        coupleRepositoryImpl: CoupleRepositoryImpl
    ): CoupleRepository

    @Binds
    @Singleton
    abstract fun bindCoupleMatchingRepository(
        coupleMatchingRepositoryImpl: CoupleMatchingRepositoryImpl
    ): CoupleMatchingRepository
}