package com.example.dulit.feature.calendar.data.remote.dto

import com.example.dulit.feature.calendar.domain.model.CreateCalendarRequest
import com.google.gson.annotations.SerializedName

/**
 * 캘린더 일정 생성 요청 DTO
 * 서버 API 요청에 사용
 */
data class CreateCalendarRequestDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("date")
    val date: String,  // ISO 8601 형식: "2025-03-30T00:00:00Z"

    @SerializedName("filePaths")
    val filePaths: List<String>? = null  // 선택값
)

/**
 * Domain Model → DTO 변환
 */
fun CreateCalendarRequest.toDto(): CreateCalendarRequestDto {
    return CreateCalendarRequestDto(
        title = title,
        description = description,
        date = date,  // ISO 8601 형식으로 변환됨
        filePaths = filePaths
    )
}