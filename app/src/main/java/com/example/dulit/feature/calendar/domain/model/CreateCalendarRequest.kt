package com.example.dulit.feature.calendar.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CreateCalendarRequest(
    val title: String,
    val description: String,
    val date: String,  // ISO 8601: "2025-03-30T00:00:00Z"
    val filePaths: List<String>? = null
) {
//    companion object {
//        /**
//         * LocalDate를 ISO 8601 형식 문자열로 변환
//         * @param localDate 날짜
//         * @return "2025-03-30T00:00:00Z"
//         */
//        fun formatDate(localDate: LocalDate): String {
//            return "${localDate}T00:00:00Z"
//        }
//    }
}