package com.example.dulit.feature.calendar.domain.model

/**
 * 캘린더 일정 수정 요청 (Domain Model)
 * 모든 필드가 선택값
 */
data class UpdateCalendarRequest(
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    val filePaths: List<String>? = null
)