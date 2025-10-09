// di/RepositoryModule.kt
package com.example.dulit.di

import com.example.dulit.feature.auth.data.repository.AuthRepositoryImpl
import com.example.dulit.feature.auth.domain.repository.AuthRepository
import com.example.dulit.feature.couple.data.repository.CoupleMatchingRepositoryImpl
import com.example.dulit.feature.couple.data.repository.CoupleRepositoryImpl
import com.example.dulit.feature.couple.domain.repository.CoupleMatchingRepository
import com.example.dulit.feature.couple.domain.repository.CoupleRepository
import com.example.dulit.feature.user.data.repository.UserRepositoryImpl
import com.example.dulit.feature.user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // ⭐ Auth Repository
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    // ⭐ User Repository
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    // ⭐ Couple Repository
    @Binds
    @Singleton
    abstract fun bindCoupleRepository(
        coupleRepositoryImpl: CoupleRepositoryImpl
    ): CoupleRepository

    // ⭐ Couple Matching Repository
    @Binds
    @Singleton
    abstract fun bindCoupleMatchingRepository(
        coupleMatchingRepositoryImpl: CoupleMatchingRepositoryImpl
    ): CoupleMatchingRepository
}