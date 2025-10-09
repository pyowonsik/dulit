// feature/calendar/domain/usecase/GetCalendarsUseCase.kt
package com.example.dulit.feature.calendar.domain.usecase

import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

/**
 * 캘린더 일정 목록 조회 UseCase
 * @param month 조회할 월 (null이면 전체)
 */
class GetCalendarsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(month: Int? = null): Result<List<Calendar>> {
        return calendarRepository.findAll(month)
    }
}