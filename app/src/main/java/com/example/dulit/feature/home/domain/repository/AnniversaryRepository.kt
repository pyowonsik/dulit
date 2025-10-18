package com.example.dulit.feature.home.domain.repository

import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.CreateAnniversaryRequest
import com.example.dulit.feature.home.domain.model.UpdateAnniversaryRequest

/**
 * Anniversary Repository Interface
 */
interface AnniversaryRepository {
    
    /**
     * 기념일 작성
     */
    suspend fun createAnniversary(request: CreateAnniversaryRequest): Result<Anniversary>
    
    /**
     * 전체 기념일 조회
     * @param title 제목 검색 (optional)
     * @return 날짜순(ASC)으로 정렬된 전체 기념일 리스트
     */
    suspend fun findAllAnniversaries(
        title: String? = null
    ): Result<List<Anniversary>>
    
    /**
     * 단건 기념일 조회
     */
    suspend fun findOneAnniversary(anniversaryId: Int): Result<Anniversary>
    
    /**
     * 기념일 수정
     */
    suspend fun updateAnniversary(anniversaryId: Int, request: UpdateAnniversaryRequest): Result<Anniversary>
    
    /**
     * 기념일 삭제
     * @return 삭제된 anniversaryId
     */
    suspend fun deleteAnniversary(anniversaryId: Int): Result<Int>
}
