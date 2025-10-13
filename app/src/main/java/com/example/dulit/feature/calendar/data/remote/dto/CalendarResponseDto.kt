package com.example.dulit.feature.calendar.data.remote.dto

import com.example.dulit.feature.calendar.domain.model.Calendar
import com.google.gson.annotations.SerializedName

/**
 * 캘린더 응답 DTO
 * 서버로부터 받는 데이터
 */
data class CalendarResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("date")
    val date: String,  // ISO 8601: "2025-03-30T00:00:00Z"

    @SerializedName("filePaths")
    val filePaths: List<String>?
)

/**
 * DTO → Domain Model 변환
 */
fun CalendarResponseDto.toDomain(): Calendar {
    return Calendar(
        id = id,
        title = title,
        description = description,
        date = date,
        filePaths = filePaths ?: emptyList()
    )
}