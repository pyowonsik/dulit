package com.example.dulit.feature.home.data.model

import com.google.gson.annotations.SerializedName

/**
 * 약속 응답 DTO
 */
data class PlanResponseDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("topic")
    val topic: String,
    
    @SerializedName("location")
    val location: String,
    
    @SerializedName("time")
    val time: String,  // ISO 8601 형식: "2025-02-07T06:30:05.016Z"
    
    @SerializedName("createdAt")
    val createdAt: String,
    
    @SerializedName("updatedAt")
    val updatedAt: String
)

/**
 * DTO → Domain Model 변환
 */
fun PlanResponseDto.toDomain(): com.example.dulit.feature.home.domain.model.Plan {
    return com.example.dulit.feature.home.domain.model.Plan(
        id = id,
        topic = topic,
        location = location,
        time = time,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

