// di/DatabaseModule.kt
package com.example.dulit.di

import android.content.Context
import androidx.room.Room
import com.example.dulit.core.local.database.AppDatabase
import com.example.dulit.feature.home.data.local.dao.AnniversaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dulit_database"
        )
            .fallbackToDestructiveMigration() // 개발 중에만
            .build()
    }

    @Provides
    @Singleton
    fun provideAnniversaryDao(database: AppDatabase): AnniversaryDao {
        return database.anniversaryDao()
    }
}