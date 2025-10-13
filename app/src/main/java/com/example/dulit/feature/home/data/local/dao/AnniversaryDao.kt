package com.example.dulit.feature.home.data.local.dao

import androidx.room.*
import com.example.dulit.feature.home.data.local.entity.AnniversaryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for Anniversary
 * 
 * Anniversary 테이블에 대한 CRUD 작업을 정의
 */
@Dao
interface AnniversaryDao {
    
    /**
     * 모든 Anniversary 조회 (날짜 오름차순)
     * Flow로 반환하여 데이터 변경 시 자동 업데이트
     */
    @Query("SELECT * FROM anniversaries ORDER BY date ASC")
    fun getAllFlow(): Flow<List<AnniversaryEntity>>
    
    /**
     * 특정 Anniversary 조회
     * 
     * @param id Anniversary ID
     * @return AnniversaryEntity 또는 null
     */
    @Query("SELECT * FROM anniversaries WHERE id = :id")
    suspend fun getById(id: Int): AnniversaryEntity?
    
    /**
     * Anniversary 단건 삽입 (충돌 시 교체)
     * 
     * @param anniversary 삽입할 Anniversary
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anniversary: AnniversaryEntity)
    
    /**
     * Anniversary 여러 건 삽입 (충돌 시 교체)
     * 서버 동기화 시 사용
     * 
     * @param anniversaries 삽입할 Anniversary 리스트
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anniversaries: List<AnniversaryEntity>)
    
    /**
     * Anniversary 업데이트
     * 
     * @param anniversary 업데이트할 Anniversary
     */
    @Update
    suspend fun update(anniversary: AnniversaryEntity)
    
    /**
     * 특정 Anniversary 삭제
     * 
     * @param id 삭제할 Anniversary ID
     */
    @Query("DELETE FROM anniversaries WHERE id = :id")
    suspend fun deleteById(id: Int)
    
    /**
     * 모든 Anniversary 삭제
     */
    @Query("DELETE FROM anniversaries")
    suspend fun deleteAll()
    
    /**
     * 전체 Anniversary 교체 (동기화용)
     * 기존 데이터 삭제 후 새 데이터 삽입
     * 
     * @param anniversaries 새로운 Anniversary 리스트
     */
    @Transaction
    suspend fun replaceAll(anniversaries: List<AnniversaryEntity>) {
        deleteAll()
        insertAll(anniversaries)
    }
}

