package com.example.dulit.core.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dulit.feature.home.data.local.dao.AnniversaryDao
import com.example.dulit.feature.home.data.local.entity.AnniversaryEntity
import com.example.dulit.feature.calendar.data.local.dao.CalendarDao
import com.example.dulit.feature.calendar.data.local.entity.CalendarEntity

/**
 * Dulit App의 Room Database
 * 
 * 모든 Entity와 DAO를 관리하는 전역 데이터베이스
 */
@Database(
    entities = [
        AnniversaryEntity::class,
        CalendarEntity::class, // ✅ 추가
        // PlanEntity::class, // 나중에 추가
        // ChatMessageEntity::class, // 나중에 추가
    ],
    version = 2, // ✅ 버전 올림
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun anniversaryDao(): AnniversaryDao
    abstract fun calendarDao(): CalendarDao // ✅ 추가
    // abstract fun planDao(): PlanDao
    // abstract fun chatDao(): ChatDao
}