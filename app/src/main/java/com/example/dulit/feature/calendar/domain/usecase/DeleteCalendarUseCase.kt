// feature/calendar/domain/usecase/DeleteCalendarUseCase.kt
package com.example.dulit.feature.calendar.domain.usecase

import com.example.dulit.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

/**
 * 캘린더 일정 삭제 UseCase
 */
class DeleteCalendarUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(calendarId: Int): Result<Unit> {
        return calendarRepository.remove(calendarId)
    }
}