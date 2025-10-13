package com.example.dulit.feature.home.data.repository

import android.util.Log
import com.example.dulit.feature.home.data.remote.api.AnniversaryApi
import com.example.dulit.feature.home.data.remote.dto.toDomain
import com.example.dulit.feature.home.data.remote.dto.toDto
import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.CreateAnniversaryRequest
import com.example.dulit.feature.home.domain.model.UpdateAnniversaryRequest
import com.example.dulit.feature.home.domain.repository.AnniversaryRepository
import javax.inject.Inject

class AnniversaryRepositoryImpl @Inject constructor(
    private val anniversaryApi: AnniversaryApi
) : AnniversaryRepository {

    override suspend fun createAnniversary(request: CreateAnniversaryRequest): Result<Anniversary> = runCatching {
        Log.d("AnniversaryRepository", "기념일 작성 API 호출")
        val response = anniversaryApi.createAnniversary(request.toDto())
        if (!response.isSuccessful || response.body() == null) {
            Log.e("AnniversaryRepository", "❌ 기념일 작성 실패: ${response.code()}")
            throw Exception("기념일 작성 실패: ${response.code()}")
        }
        val anniversary = response.body()!!.toDomain()
        Log.d("AnniversaryRepository", "✅ 기념일 작성 성공: ${anniversary.title}")
        anniversary
    }.onFailure { e ->
        Log.e("AnniversaryRepository", "기념일 작성 에러", e)
    }

    override suspend fun findAllAnniversaries(
        page: Int?,
        take: Int?,
        order: String?,
        title: String?
    ): Result<List<Anniversary>> = runCatching {
        Log.d("AnniversaryRepository", "기념일 전체 조회 API 호출 (page=$page, take=$take, order=$order, title=$title)")
        val response = anniversaryApi.findAllAnniversaries(page, take, order, title)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("AnniversaryRepository", "❌ 기념일 조회 실패: ${response.code()}")
            throw Exception("기념일 조회 실패: ${response.code()}")
        }
        val anniversaries = response.body()!!.map { it.toDomain() }
        Log.d("AnniversaryRepository", "✅ 기념일 ${anniversaries.size}개 조회 성공")
        anniversaries
    }.onFailure { e ->
        Log.e("AnniversaryRepository", "기념일 조회 에러", e)
    }

    override suspend fun findOneAnniversary(anniversaryId: Int): Result<Anniversary> = runCatching {
        Log.d("AnniversaryRepository", "기념일 단건 조회 API 호출 (anniversaryId=$anniversaryId)")
        val response = anniversaryApi.findOneAnniversary(anniversaryId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("AnniversaryRepository", "❌ 기념일 조회 실패: ${response.code()}")
            throw Exception("기념일 조회 실패: ${response.code()}")
        }
        val anniversary = response.body()!!.toDomain()
        Log.d("AnniversaryRepository", "✅ 기념일 조회 성공: ${anniversary.title}")
        anniversary
    }.onFailure { e ->
        Log.e("AnniversaryRepository", "기념일 조회 에러", e)
    }

    override suspend fun updateAnniversary(anniversaryId: Int, request: UpdateAnniversaryRequest): Result<Anniversary> = runCatching {
        Log.d("AnniversaryRepository", "기념일 수정 API 호출 (anniversaryId=$anniversaryId)")
        val response = anniversaryApi.updateAnniversary(anniversaryId, request.toDto())
        if (!response.isSuccessful || response.body() == null) {
            Log.e("AnniversaryRepository", "❌ 기념일 수정 실패: ${response.code()}")
            throw Exception("기념일 수정 실패: ${response.code()}")
        }
        val anniversary = response.body()!!.toDomain()
        Log.d("AnniversaryRepository", "✅ 기념일 수정 성공: ${anniversary.title}")
        anniversary
    }.onFailure { e ->
        Log.e("AnniversaryRepository", "기념일 수정 에러", e)
    }

    override suspend fun deleteAnniversary(anniversaryId: Int): Result<Int> = runCatching {
        Log.d("AnniversaryRepository", "기념일 삭제 API 호출 (anniversaryId=$anniversaryId)")
        val response = anniversaryApi.deleteAnniversary(anniversaryId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("AnniversaryRepository", "❌ 기념일 삭제 실패: ${response.code()}")
            throw Exception("기념일 삭제 실패: ${response.code()}")
        }
        val deletedId = response.body()!!
        Log.d("AnniversaryRepository", "✅ 기념일 삭제 성공 (deletedId=$deletedId)")
        deletedId
    }.onFailure { e ->
        Log.e("AnniversaryRepository", "기념일 삭제 에러", e)
    }
}
