package com.example.dulit.feature.home.data.remote.api

import com.example.dulit.feature.home.data.remote.dto.AnniversaryResponseDto
import com.example.dulit.feature.home.data.remote.dto.CreateAnniversaryRequestDto
import com.example.dulit.feature.home.data.remote.dto.UpdateAnniversaryRequestDto
import retrofit2.Response
import retrofit2.http.*

interface AnniversaryApi {

    /**
     * 기념일 작성
     * POST /couple/anniversary
     */
    @POST("/couple/anniversary")
    suspend fun createAnniversary(
        @Body request: CreateAnniversaryRequestDto
    ): Response<AnniversaryResponseDto>

    /**
     * 전체 기념일 조회 (페이지네이션)
     * GET /couple/anniversary?page=1&take=10&order=ASC&title=
     */
    @GET("/couple/anniversary")
    suspend fun findAllAnniversaries(
        @Query("page") page: Int? = null,
        @Query("take") take: Int? = null,
        @Query("order") order: String? = null,
        @Query("title") title: String? = null
    ): Response<List<AnniversaryResponseDto>>

    /**
     * 단건 기념일 조회
     * GET /couple/anniversary/:anniversaryId
     */
    @GET("/couple/anniversary/{anniversaryId}")
    suspend fun findOneAnniversary(
        @Path("anniversaryId") anniversaryId: Int
    ): Response<AnniversaryResponseDto>

    /**
     * 기념일 수정
     * PATCH /couple/anniversary/:anniversaryId
     */
    @PATCH("/couple/anniversary/{anniversaryId}")
    suspend fun updateAnniversary(
        @Path("anniversaryId") anniversaryId: Int,
        @Body request: UpdateAnniversaryRequestDto
    ): Response<AnniversaryResponseDto>

    /**
     * 기념일 삭제
     * DELETE /couple/anniversary/:anniversaryId
     * @return 삭제된 anniversaryId
     */
    @DELETE("/couple/anniversary/{anniversaryId}")
    suspend fun deleteAnniversary(
        @Path("anniversaryId") anniversaryId: Int
    ): Response<Int>
}
