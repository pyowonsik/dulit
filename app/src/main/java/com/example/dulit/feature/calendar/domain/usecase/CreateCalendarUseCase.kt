// feature/calendar/domain/usecase/CreateCalendarUseCase.kt
package com.example.dulit.feature.calendar.domain.usecase

import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.domain.model.CreateCalendarRequest
import com.example.dulit.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

/**
 * 캘린더 일정 생성 UseCase
 */
class CreateCalendarUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(request: CreateCalendarRequest): Result<Calendar> {
        return calendarRepository.create(request)
    }
}