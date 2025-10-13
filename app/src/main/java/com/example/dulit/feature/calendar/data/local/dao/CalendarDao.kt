package com.example.dulit.feature.calendar.data.local.dao

import androidx.room.*
import com.example.dulit.feature.calendar.data.local.entity.CalendarEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for Calendar
 * 
 * Calendar 테이블에 대한 CRUD 작업을 정의
 */
@Dao
interface CalendarDao {
    
    /**
     * 모든 Calendar 조회 (날짜 내림차순)
     * Flow로 반환하여 데이터 변경 시 자동 업데이트
     */
    @Query("SELECT * FROM calendars ORDER BY date DESC")
    fun getAllFlow(): Flow<List<CalendarEntity>>
    
    /**
     * 월별 Calendar 조회 (가장 자주 사용)
     * 
     * @param month 월 (1-12)
     * @return 해당 월의 Calendar 리스트 (날짜 오름차순)
     */
    @Query("SELECT * FROM calendars WHERE month = :month ORDER BY date ASC")
    fun getByMonthFlow(month: Int): Flow<List<CalendarEntity>>
    
    /**
     * 특정 날짜의 Calendar 조회
     * 
     * @param date ISO 8601 형식의 날짜 (예: "2025-03-30T00:00:00Z")
     * @return 해당 날짜의 Calendar 리스트
     */
    @Query("SELECT * FROM calendars WHERE date LIKE :date || '%' ORDER BY date ASC")
    fun getByDateFlow(date: String): Flow<List<CalendarEntity>>
    
    /**
     * 특정 Calendar 조회 (단건)
     * 
     * @param id Calendar ID
     * @return CalendarEntity 또는 null
     */
    @Query("SELECT * FROM calendars WHERE id = :id")
    suspend fun getById(id: Int): CalendarEntity?
    
    /**
     * Calendar 단건 삽입 (충돌 시 교체)
     * 
     * @param calendar 삽입할 Calendar
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(calendar: CalendarEntity)
    
    /**
     * Calendar 여러 건 삽입 (충돌 시 교체)
     * 서버 동기화 시 사용
     * 
     * @param calendars 삽입할 Calendar 리스트
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(calendars: List<CalendarEntity>)
    
    /**
     * Calendar 업데이트
     * 
     * @param calendar 업데이트할 Calendar
     */
    @Update
    suspend fun update(calendar: CalendarEntity)
    
    /**
     * 특정 Calendar 삭제
     * 
     * @param id 삭제할 Calendar ID
     */
    @Query("DELETE FROM calendars WHERE id = :id")
    suspend fun deleteById(id: Int)
    
    /**
     * 특정 월의 모든 Calendar 삭제
     * 
     * @param month 월 (1-12)
     */
    @Query("DELETE FROM calendars WHERE month = :month")
    suspend fun deleteByMonth(month: Int)
    
    /**
     * 모든 Calendar 삭제
     */
    @Query("DELETE FROM calendars")
    suspend fun deleteAll()
    
    /**
     * 전체 Calendar 교체 (동기화용)
     * 기존 데이터 삭제 후 새 데이터 삽입
     * 
     * @param calendars 새로운 Calendar 리스트
     */
    @Transaction
    suspend fun replaceAll(calendars: List<CalendarEntity>) {
        deleteAll()
        insertAll(calendars)
    }
    
    /**
     * 특정 월의 Calendar 교체 (월별 동기화용)
     * 기존 월 데이터 삭제 후 새 데이터 삽입
     * 
     * @param month 월 (1-12)
     * @param calendars 새로운 Calendar 리스트
     */
    @Transaction
    suspend fun replaceMonth(month: Int, calendars: List<CalendarEntity>) {
        deleteByMonth(month)
        insertAll(calendars)
    }
}
