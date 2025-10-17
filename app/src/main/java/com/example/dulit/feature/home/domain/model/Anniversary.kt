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
    /**
     * 날짜를 LocalDate로 변환
     */
    fun getLocalDate(): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    /**
     * 날짜 포맷 변환
     * @param pattern 날짜 형식 (기본: "yyyy년 MM월 dd일")
     */
    fun getFormattedDate(pattern: String = "yyyy년 MM월 dd일"): String {
        return try {
            val localDate = getLocalDate()
            localDate.format(DateTimeFormatter.ofPattern(pattern))
        } catch (e: Exception) {
            date
        }
    }

    /**
     * D-Day 계산
     * 
     * 계산 방식:
     * - 과거: 시작일을 1일째로 계산 (2022-08-04 만난 날 = D+1, 어제 = D+2)
     * - 오늘: D-Day (0)
     * - 미래: 남은 일수 (내일 = -1 = D-1, 모레 = -2 = D-2)
     * 
     * @return 양수면 과거부터 경과일, 0이면 오늘, 음수면 미래까지 남은 일
     */
    fun getDDay(): Long {
        val today = LocalDate.now()
        val anniversaryDate = getLocalDate()
        val days = java.time.temporal.ChronoUnit.DAYS.between(anniversaryDate, today)
        
        return when {
            days > 0 -> days + 1  // 과거: 시작일 포함 (만난 날 = D+1, 어제 = D+2)
            days == 0L -> 0       // 오늘: D-Day
            else -> days          // 미래: 그대로 (내일 = -1 = D-1)
        }
    }

    /**
     * D-Day 문자열 표현
     * @return "D+1171" (과거), "D-Day" (오늘), "D-7" (미래) 형식
     */
    fun getDDayText(): String {
        val dDay = getDDay()
        return when {
            dDay > 0 -> "D+${dDay}"     // 과거 (만난 지 N일째)
            dDay == 0L -> "D-Day"       // 오늘 (기념일 당일)
            else -> "D${dDay}"          // 미래 (N일 남음, 음수라 자동으로 -부호)
        }
    }

    /**
     * 기념일이 지났는지 확인 (과거)
     */
    val isPast: Boolean
        get() = getDDay() > 0

    /**
     * 기념일이 오늘인지 확인
     */
    val isToday: Boolean
        get() = getDDay() == 0L

    /**
     * 기념일이 다가오는지 확인 (미래)
     */
    val isUpcoming: Boolean
        get() = getDDay() < 0
}



