package com.example.dulit.core.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dulit.feature.home.data.local.dao.AnniversaryDao
import com.example.dulit.feature.home.data.local.entity.AnniversaryEntity

/**
 * Dulit App의 Room Database
 * 
 * 모든 Entity와 DAO를 관리하는 전역 데이터베이스
 */
@Database(
    entities = [
        AnniversaryEntity::class,
        // PlanEntity::class, // 나중에 추가
        // CalendarEntity::class, // 나중에 추가
        // ChatMessageEntity::class, // 나중에 추가
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun anniversaryDao(): AnniversaryDao
    // abstract fun planDao(): PlanDao
    // abstract fun calendarDao(): CalendarDao
    // abstract fun chatDao(): ChatDao
}