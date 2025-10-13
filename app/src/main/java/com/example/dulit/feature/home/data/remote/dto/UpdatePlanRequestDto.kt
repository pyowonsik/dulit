package com.example.dulit.feature.home.data.remote.dto

import com.example.dulit.feature.home.domain.model.UpdatePlanRequest
import com.google.gson.annotations.SerializedName

/**
 * 약속 수정 요청 DTO (모든 필드 optional)
 */
data class UpdatePlanRequestDto(
    @SerializedName("topic")
    val topic: String? = null,
    
    @SerializedName("location")
    val location: String? = null,
    
    @SerializedName("time")
    val time: String? = null  // ISO 8601 형식: "2025-02-07T06:30:05.016Z"
)

/**
 * Domain Model → DTO 변환
 */
fun UpdatePlanRequest.toDto(): UpdatePlanRequestDto {
    return UpdatePlanRequestDto(
        topic = topic,
        location = location,
        time = time
    )
}

