// feature/calendar/domain/repository/CalendarRepository.kt
package com.example.dulit.feature.calendar.domain.repository

import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.domain.model.CreateCalendarRequest
import com.example.dulit.feature.calendar.domain.model.UpdateCalendarRequest

/**
 * 캘린더 Repository 인터페이스
 * Domain Layer에서 정의
 */
interface CalendarRepository {

    /**
     * 캘린더 일정 생성
     */
    suspend fun create(request: CreateCalendarRequest): Result<Calendar>

    /**
     * 캘린더 일정 목록 조회
     * @param month 조회할 월 (null이면 전체)
     */
    suspend fun findAll(month: Int?): Result<List<Calendar>>

    /**
     * 캘린더 일정 단건 조회
     */
    suspend fun findOne(calendarId: Int): Result<Calendar>

    /**
     * 캘린더 일정 수정
     */
    suspend fun update(calendarId: Int, request: UpdateCalendarRequest): Result<Calendar>

    /**
     * 캘린더 일정 삭제
     */
    suspend fun remove(calendarId: Int): Result<Unit>
}