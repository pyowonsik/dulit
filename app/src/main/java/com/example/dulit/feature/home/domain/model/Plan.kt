package com.example.dulit.feature.home.domain.model

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * 약속 Domain Model
 */
data class Plan(
    val id: Int,
    val topic: String,
    val location: String,
    val time: String,  // ISO 8601 형식: "2025-02-07T06:30:05.016Z"
    val createdAt: String,
    val updatedAt: String
) {
    /**
     * 약속 시간을 LocalDateTime으로 변환 (UTC → 한국 시간)
     */
    fun getLocalDateTime(): LocalDateTime {
        return try {
            // ISO 8601 UTC 시간 파싱 후 한국 시간(Asia/Seoul)으로 변환
            val instant = Instant.parse(time)
            instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
        } catch (e: Exception) {
            // Fallback: Z 없는 형식도 처리
            LocalDateTime.parse(time.removeSuffix("Z"), DateTimeFormatter.ISO_DATE_TIME)
        }
    }

    /**
     * 약속 시간 포맷 변환
     * @param pattern 날짜 형식 (기본: "yyyy년 MM월 dd일 HH시 mm분")
     */
    fun getFormattedTime(pattern: String = "yyyy년 MM월 dd일 HH시 mm분"): String {
        return try {
            val dateTime = getLocalDateTime()
            dateTime.format(DateTimeFormatter.ofPattern(pattern))
        } catch (e: Exception) {
            time
        }
    }

    /**
     * 남은 시간 계산 (분 단위)
     * @return 양수면 미래, 0이면 현재, 음수면 지난 약속
     */
    fun getRemainingMinutes(): Long {
        val now = LocalDateTime.now()
        val planTime = getLocalDateTime()
        return ChronoUnit.MINUTES.between(now, planTime)
    }

    /**
     * 남은 시간 계산 (시간 단위)
     */
    fun getRemainingHours(): Long {
        val now = LocalDateTime.now()
        val planTime = getLocalDateTime()
        return ChronoUnit.HOURS.between(now, planTime)
    }

    /**
     * 남은 시간 계산 (일 단위)
     */
    fun getRemainingDays(): Long {
        val now = LocalDateTime.now()
        val planTime = getLocalDateTime()
        return ChronoUnit.DAYS.between(now, planTime)
    }

    /**
     * 남은 시간 텍스트 표현
     * @return "5분 후", "3시간 후", "2일 후", "1시간 전" 등
     */
    fun getRemainingTimeText(): String {
        val minutes = getRemainingMinutes()
        
        return when {
            minutes > 0 -> {
                when {
                    minutes < 60 -> "${minutes}분 후"
                    minutes < 1440 -> "${minutes / 60}시간 후"
                    else -> "${minutes / 1440}일 후"
                }
            }
            minutes == 0L -> "지금"
            else -> {
                val absMinutes = -minutes
                when {
                    absMinutes < 60 -> "${absMinutes}분 전"
                    absMinutes < 1440 -> "${absMinutes / 60}시간 전"
                    else -> "${absMinutes / 1440}일 전"
                }
            }
        }
    }

    /**
     * 약속이 지났는지 확인
     */
    val isPast: Boolean
        get() = getRemainingMinutes() < 0

    /**
     * 약속이 임박했는지 확인 (1시간 이내)
     */
    val isImminent: Boolean
        get() {
            val minutes = getRemainingMinutes()
            return minutes in 0..60
        }

    /**
     * 약속이 다가오는지 확인 (미래)
     */
    val isUpcoming: Boolean
        get() = getRemainingMinutes() > 0

    /**
     * 오늘의 약속인지 확인
     */
    val isToday: Boolean
        get() {
            val now = LocalDateTime.now()
            val planTime = getLocalDateTime()
            return now.toLocalDate() == planTime.toLocalDate()
        }
}







