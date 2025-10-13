package com.example.dulit.feature.calendar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dulit.feature.calendar.domain.model.Calendar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room Database Entity for Calendar
 * 
 * 백엔드 Calendar 테이블 구조:
 * - id: PrimaryGeneratedColumn
 * - title: string
 * - content: string
 * - date: Date
 * - filePaths: string[] (이미지 경로 배열)
 * - createdAt, updatedAt: BaseTable
 */
@Entity(tableName = "calendars")
data class CalendarEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val date: String, // ISO 8601: "2025-03-30T00:00:00Z"
    val month: Int, // 빠른 월별 조회를 위한 인덱스 (1-12)
    val filePaths: String?, // JSON array string: ["path1", "path2"]
    val createdAt: String,
    val updatedAt: String
) {
    /**
     * Entity → Domain Model 변환
     */
    fun toDomain() = Calendar(
        id = id,
        title = title,
        description = description,
        date = date,
        filePaths = filePaths?.let { 
            try {
                val type = object : TypeToken<List<String>>() {}.type
                Gson().fromJson<List<String>>(it, type)
            } catch (e: Exception) {
                emptyList()
            }
        } ?: emptyList()
    )
    
    companion object {
        /**
         * Domain Model → Entity 변환 (Room 저장용)
         */
        fun fromDomain(
            calendar: Calendar,
            createdAt: String,
            updatedAt: String
        ): CalendarEntity {
            // date에서 month 추출 (ISO 8601: "2025-03-30T...")
            val month = calendar.date.substring(5, 7).toIntOrNull() ?: 1
            
            return CalendarEntity(
                id = calendar.id,
                title = calendar.title,
                description = calendar.description,
                date = calendar.date,
                month = month,
                filePaths = if (calendar.filePaths.isNotEmpty()) {
                    Gson().toJson(calendar.filePaths)
                } else null,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
