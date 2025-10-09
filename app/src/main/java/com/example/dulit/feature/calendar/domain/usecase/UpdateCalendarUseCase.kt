// feature/calendar/domain/usecase/UpdateCalendarUseCase.kt
package com.example.dulit.feature.calendar.domain.usecase

import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.domain.model.UpdateCalendarRequest
import com.example.dulit.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

/**
 * 캘린더 일정 수정 UseCase
 */
class UpdateCalendarUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        calendarId: Int,
        request: UpdateCalendarRequest
    ): Result<Calendar> {
        return calendarRepository.update(calendarId, request)
    }
}