package com.example.dulit.feature.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.dulit.feature.home.data.local.entity.PlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {

    /**
     * 모든 Plan 조회 (날짜 오름차순)
     * Flow로 반환하여 데이터 변경 시 자동 업데이트
     */
    @Query("SELECT * FROM plans ORDER BY time ASC")
    fun getAllFlow(): Flow<List<PlanEntity>>

    /**
     * 특정 Plan 조회
     *
     * @param id Plan ID
     * @return PlanEntity 또는 null
     */
    @Query("SELECT * FROM plans WHERE id = :id")
    suspend fun getById(id: Int): PlanEntity?

    /**
     * Plan 단건 삽입 (충돌 시 교체)
     *
     * @param Plan 삽입할 Plan
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: PlanEntity)

    /**
     * Plan 여러 건 삽입 (충돌 시 교체)
     * 서버 동기화 시 사용
     *
     * @param plans 삽입할 Plan 리스트
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plans: List<PlanEntity>)

    /**
     * Plan 업데이트
     *
     * @param plan 업데이트할 Plan
     */
    @Update
    suspend fun update(plan: PlanEntity)

    /**
     * 특정 Plan 삭제
     *
     * @param id 삭제할 Plan ID
     */
    @Query("DELETE FROM plans WHERE id = :id")
    suspend fun deleteById(id: Int)

    /**
     * 모든 Plan 삭제
     */
    @Query("DELETE FROM plans")
    suspend fun deleteAll()

    /**
     * 전체 Plan 교체 (동기화용)
     * 기존 데이터 삭제 후 새 데이터 삽입
     *
     * @param plans 새로운 Plan 리스트
     */
    @Transaction
    suspend fun replaceAll(plans: List<PlanEntity>) {
        deleteAll()
        insertAll(plans)
    }
}