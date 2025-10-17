package com.example.dulit.feature.home.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 약속 생성 요청 Domain Model
 */
data class CreatePlanRequest(
    val topic: String,
    val location: String,
    val time: String  // ISO 8601 형식
) {
    companion object {
        /**
         * LocalDateTime을 백엔드 형식 문자열로 변환
         * @return "2025-02-07T06:30:05.016Z" 형식
         */
        fun formatTime(dateTime: LocalDateTime): String {
            return dateTime.format(DateTimeFormatter.ISO_DATE_TIME) + "Z"
        }
    }
}



