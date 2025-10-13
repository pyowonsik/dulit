// feature/calendar/data/repository/CalendarRepositoryImpl.kt
package com.example.dulit.feature.calendar.data.repository

import com.example.dulit.feature.calendar.data.remote.api.CalendarApi
import com.example.dulit.feature.calendar.data.remote.dto.toDomain
import com.example.dulit.feature.calendar.data.remote.dto.toDto
import com.example.dulit.feature.calendar.domain.model.Calendar
import com.example.dulit.feature.calendar.domain.model.CreateCalendarRequest
import com.example.dulit.feature.calendar.domain.model.UpdateCalendarRequest
import com.example.dulit.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject

/**
 * CalendarRepository 구현체
 */
class CalendarRepositoryImpl @Inject constructor(
    private val calendarApi: CalendarApi
) : CalendarRepository {

    override suspend fun create(
        request: CreateCalendarRequest
    ): Result<Calendar> = runCatching {
        val response = calendarApi.create(request.toDto())

        if (!response.isSuccessful || response.body() == null) {
            throw Exception("일정 생성 실패: ${response.code()}")
        }

        response.body()!!.toDomain()
    }

    override suspend fun findAll(
        month: Int?
    ): Result<List<Calendar>> = runCatching {
        val response = calendarApi.findAll(month)

        if (!response.isSuccessful || response.body() == null) {
            throw Exception("일정 조회 실패: ${response.code()}")
        }

        response.body()!!.map { it.toDomain() }
    }

    override suspend fun findOne(
        calendarId: Int
    ): Result<Calendar> = runCatching {
        val response = calendarApi.findOne(calendarId)

        if (!response.isSuccessful || response.body() == null) {
            throw Exception("일정 조회 실패: ${response.code()}")
        }

        response.body()!!.toDomain()
    }

    override suspend fun update(
        calendarId: Int,
        request: UpdateCalendarRequest
    ): Result<Calendar> = runCatching {
        val response = calendarApi.update(calendarId, request.toDto())

        if (!response.isSuccessful || response.body() == null) {
            throw Exception("일정 수정 실패: ${response.code()}")
        }

        response.body()!!.toDomain()
    }

    override suspend fun remove(
        calendarId: Int
    ): Result<Unit> = runCatching {
        val response = calendarApi.remove(calendarId)

        if (!response.isSuccessful) {
            throw Exception("일정 삭제 실패: ${response.code()}")
        }
    }
}