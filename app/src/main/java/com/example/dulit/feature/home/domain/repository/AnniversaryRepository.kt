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
     * @param page 페이지 번호 (optional)
     * @param take 가져올 개수 (optional)
     * @param order 정렬 순서 (ASC/DESC, optional)
     * @param title 제목 필터 (optional)
     */
    suspend fun findAllAnniversaries(
        page: Int? = null,
        take: Int? = null,
        order: String? = null,
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
