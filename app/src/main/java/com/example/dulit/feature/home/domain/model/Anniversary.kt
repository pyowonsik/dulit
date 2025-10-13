package com.example.dulit.feature.home.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 기념일 Domain Model
 */
data class Anniversary(
    val id: Int,
    val title: String,
    val date: String,  // "2025-02-07" 형식
    val createdAt: String,
    val updatedAt: String
) {
//    /**
//     * 날짜를 LocalDate로 변환
//     */
//    fun getLocalDate(): LocalDate {
//        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
//    }
//
//    /**
//     * 날짜 포맷 변환
//     * @param pattern 날짜 형식 (기본: "yyyy년 MM월 dd일")
//     */
//    fun getFormattedDate(pattern: String = "yyyy년 MM월 dd일"): String {
//        return try {
//            val localDate = getLocalDate()
//            localDate.format(DateTimeFormatter.ofPattern(pattern))
//        } catch (e: Exception) {
//            date
//        }
//    }
//
//    /**
//     * D-Day 계산 (오늘로부터 며칠 남았는지)
//     * @return 양수면 D-Day 앞, 0이면 오늘, 음수면 지남
//     */
//    fun getDDay(): Long {
//        val today = LocalDate.now()
//        val anniversaryDate = getLocalDate()
//        return java.time.temporal.ChronoUnit.DAYS.between(today, anniversaryDate)
//    }
//
//    /**
//     * D-Day 문자열 표현
//     * @return "D-7", "D-Day", "D+3" 형식
//     */
//    fun getDDayText(): String {
//        val dDay = getDDay()
//        return when {
//            dDay > 0 -> "D-${dDay}"
//            dDay == 0L -> "D-Day"
//            else -> "D+${-dDay}"
//        }
//    }
//
//    /**
//     * 기념일이 지났는지 확인
//     */
//    val isPast: Boolean
//        get() = getDDay() < 0
//
//    /**
//     * 기념일이 오늘인지 확인
//     */
//    val isToday: Boolean
//        get() = getDDay() == 0L
//
//    /**
//     * 기념일이 다가오는지 확인
//     */
//    val isUpcoming: Boolean
//        get() = getDDay() > 0
}

