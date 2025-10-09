package com.example.dulit.feature.calendar.data.model

import com.example.dulit.feature.calendar.domain.model.UpdateCalendarRequest
import com.google.gson.annotations.SerializedName

/**
 * 캘린더 일정 수정 요청 DTO
 * 모든 필드가 선택값 (수정할 것만 보냄)
 */
data class UpdateCalendarRequestDto(
    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("filePaths")
    val filePaths: List<String>? = null
)

/**
 * Domain Model → DTO 변환
 */
fun UpdateCalendarRequest.toDto(): UpdateCalendarRequestDto {
    return UpdateCalendarRequestDto(
        title = title,
        description = description,
        date = date,
        filePaths = filePaths
    )
}