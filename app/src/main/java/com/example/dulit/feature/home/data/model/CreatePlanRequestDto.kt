package com.example.dulit.feature.home.data.model

import com.google.gson.annotations.SerializedName

/**
 * 약속 생성 요청 DTO
 */
data class CreatePlanRequestDto(
    @SerializedName("topic")
    val topic: String,
    
    @SerializedName("location")
    val location: String,
    
    @SerializedName("time")
    val time: String  // ISO 8601 형식: "2025-02-07T06:30:05.016Z"
)

/**
 * Domain Model → DTO 변환
 */
fun com.example.dulit.feature.home.domain.model.CreatePlanRequest.toDto(): CreatePlanRequestDto {
    return CreatePlanRequestDto(
        topic = topic,
        location = location,
        time = time
    )
}

