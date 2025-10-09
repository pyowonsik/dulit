// feature/calendar/domain/usecase/GetCalendarUseCase.kt
package com.example.dulit.feature.calendar.domain.usecase

import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

/**
 * 캘린더 일정 단건 조회 UseCase
 */
class GetCalendarUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(calendarId: Int): Result<Calendar> {
        return calendarRepository.findOne(calendarId)
    }
}