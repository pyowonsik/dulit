package com.example.dulit.feature.home.data.remote.dto

import com.example.dulit.feature.home.domain.model.Anniversary
import com.google.gson.annotations.SerializedName

/**
 * 기념일 응답 DTO
 */
data class AnniversaryResponseDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("date")
    val date: String,  // "2025-02-07" 형식
    
    @SerializedName("createdAt")
    val createdAt: String,
    
    @SerializedName("updatedAt")
    val updatedAt: String
)

/**
 * DTO → Domain Model 변환
 */
fun AnniversaryResponseDto.toDomain(): Anniversary {
    return Anniversary(
        id = id,
        title = title,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

