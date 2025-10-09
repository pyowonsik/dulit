package com.example.dulit.feature.calendar.data.api

import com.example.dulit.feature.calendar.data.model.CalendarResponseDto
import com.example.dulit.feature.calendar.data.model.CreateCalendarRequestDto
import com.example.dulit.feature.calendar.data.model.UpdateCalendarRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CalendarApi {

    /**
     * 캘린더 일정 작성
     * POST /couple/calendar
     */
    @POST("/couple/calendar")
    suspend fun create(
        @Body request: CreateCalendarRequestDto
    ): Response<CalendarResponseDto>

    /**
     * 캘린더 일정 조회 (월별)
     * GET /couple/calendar?month=3
     * @param month 검색 월 (선택값, 없으면 전체)
     */
    @GET("/couple/calendar")
    suspend fun findAll(
        @Query("month") month: Int? = null
    ): Response<List<CalendarResponseDto>>

    /**
     * 단건 캘린더 일정 조회
     * GET /couple/calendar/{calendarId}
     */
    @GET("/couple/calendar/{calendarId}")
    suspend fun findOne(
        @Path("calendarId") calendarId: Int
    ): Response<CalendarResponseDto>

    /**
     * 캘린더 일정 수정
     * PATCH /couple/calendar/{calendarId}
     */
    @PATCH("/couple/calendar/{calendarId}")
    suspend fun update(
        @Path("calendarId") calendarId: Int,
        @Body request: UpdateCalendarRequestDto
    ): Response<CalendarResponseDto>

    /**
     * 캘린더 일정 삭제
     * DELETE /couple/calendar/{calendarId}
     */
    @DELETE("/couple/calendar/{calendarId}")
    suspend fun remove(
        @Path("calendarId") calendarId: Int
    ): Response<Unit>
}