package com.example.dulit.feature.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dulit.feature.home.domain.model.Anniversary

/**
 * Room Database Entity for Anniversary
 * 
 * 백엔드 Anniversary 테이블 구조:
 * - id: PrimaryGeneratedColumn
 * - title: string
 * - date: Date (string으로 저장)
 * - createdAt, updatedAt: BaseTable
 */
@Entity(tableName = "anniversaries")
data class AnniversaryEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val date: String, // "2025-02-07" 형식
    val createdAt: String,
    val updatedAt: String
) {
    /**
     * Entity → Domain Model 변환
     */
    fun toDomain() = Anniversary(
        id = id,
        title = title,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    companion object {
        /**
         * Domain Model → Entity 변환 (Room 저장용)
         */
        fun fromDomain(anniversary: Anniversary) = AnniversaryEntity(
            id = anniversary.id,
            title = anniversary.title,
            date = anniversary.date,
            createdAt = anniversary.createdAt,
            updatedAt = anniversary.updatedAt
        )
    }
}
