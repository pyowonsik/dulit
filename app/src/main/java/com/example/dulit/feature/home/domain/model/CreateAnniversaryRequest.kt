package com.example.dulit.feature.home.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 기념일 작성 요청 Domain Model
 */
data class CreateAnniversaryRequest(
    val title: String,
    val date: String  // "2025-02-07" 형식
) {
    companion object {
        /**
         * LocalDate를 백엔드 형식 문자열로 변환
         */
        fun formatDate(localDate: LocalDate): String {
            return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)  // "2025-02-07"
        }
    }
}





