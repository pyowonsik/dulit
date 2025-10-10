package com.example.dulit.feature.post.data.repository

import android.util.Log
import com.example.dulit.feature.post.data.api.PostApi
import com.example.dulit.feature.post.data.model.toDomain
import com.example.dulit.feature.post.data.model.toDto
import com.example.dulit.feature.post.domain.model.CreatePostRequest
import com.example.dulit.feature.post.domain.model.Post
import com.example.dulit.feature.post.domain.model.PostLikeResponse
import com.example.dulit.feature.post.domain.model.UpdatePostRequest
import com.example.dulit.feature.post.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi
) : PostRepository {

    override suspend fun createPost(request: CreatePostRequest): Result<Post> = runCatching {
        Log.d("PostRepository", "게시글 작성 API 호출")
        val response = postApi.createPost(request.toDto())
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 게시글 작성 실패: ${response.code()}")
            throw Exception("게시글 작성 실패: ${response.code()}")
        }
        val post = response.body()!!.toDomain()
        Log.d("PostRepository", "✅ 게시글 작성 성공: ${post.title}")
        post
    }.onFailure { e ->
        Log.e("PostRepository", "게시글 작성 에러", e)
    }

    override suspend fun findAllPosts(
        page: Int?,
        take: Int?,
        order: String?
    ): Result<List<Post>> = runCatching {
        Log.d("PostRepository", "게시글 전체 조회 API 호출 (page=$page, take=$take, order=$order)")
        val response = postApi.findAllPosts(page, take, order)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 게시글 조회 실패: ${response.code()}")
            throw Exception("게시글 조회 실패: ${response.code()}")
        }
        val posts = response.body()!!.map { it.toDomain() }
        Log.d("PostRepository", "✅ 게시글 ${posts.size}개 조회 성공")
        posts
    }.onFailure { e ->
        Log.e("PostRepository", "게시글 조회 에러", e)
    }

    override suspend fun findOnePost(postId: Int): Result<Post> = runCatching {
        Log.d("PostRepository", "게시글 단건 조회 API 호출 (postId=$postId)")
        val response = postApi.findOnePost(postId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 게시글 조회 실패: ${response.code()}")
            throw Exception("게시글 조회 실패: ${response.code()}")
        }
        val post = response.body()!!.toDomain()
        Log.d("PostRepository", "✅ 게시글 조회 성공: ${post.title}")
        post
    }.onFailure { e ->
        Log.e("PostRepository", "게시글 조회 에러", e)
    }

    override suspend fun updatePost(postId: Int, request: UpdatePostRequest): Result<Post> = runCatching {
        Log.d("PostRepository", "게시글 수정 API 호출 (postId=$postId)")
        val response = postApi.updatePost(postId, request.toDto())
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 게시글 수정 실패: ${response.code()}")
            throw Exception("게시글 수정 실패: ${response.code()}")
        }
        val post = response.body()!!.toDomain()
        Log.d("PostRepository", "✅ 게시글 수정 성공: ${post.title}")
        post
    }.onFailure { e ->
        Log.e("PostRepository", "게시글 수정 에러", e)
    }

    override suspend fun deletePost(postId: Int): Result<Int> = runCatching {
        Log.d("PostRepository", "게시글 삭제 API 호출 (postId=$postId)")
        val response = postApi.deletePost(postId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 게시글 삭제 실패: ${response.code()}")
            throw Exception("게시글 삭제 실패: ${response.code()}")
        }
        val deletedPostId = response.body()!!
        Log.d("PostRepository", "✅ 게시글 삭제 성공 (deletedId=$deletedPostId)")
        deletedPostId
    }.onFailure { e ->
        Log.e("PostRepository", "게시글 삭제 에러", e)
    }

    override suspend fun likePost(postId: Int): Result<PostLikeResponse> = runCatching {
        Log.d("PostRepository", "게시글 좋아요 API 호출 (postId=$postId)")
        val response = postApi.likePost(postId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 좋아요 실패: ${response.code()}")
            throw Exception("좋아요 실패: ${response.code()}")
        }
        val likeResponse = response.body()!!.toDomain()
        Log.d("PostRepository", "✅ 좋아요 성공 (isLike=${likeResponse.isLike})")
        likeResponse
    }.onFailure { e ->
        Log.e("PostRepository", "좋아요 에러", e)
    }

    override suspend fun dislikePost(postId: Int): Result<PostLikeResponse> = runCatching {
        Log.d("PostRepository", "게시글 싫어요 API 호출 (postId=$postId)")
        val response = postApi.dislikePost(postId)
        if (!response.isSuccessful || response.body() == null) {
            Log.e("PostRepository", "❌ 싫어요 실패: ${response.code()}")
            throw Exception("싫어요 실패: ${response.code()}")
        }
        val likeResponse = response.body()!!.toDomain()
        Log.d("PostRepository", "✅ 싫어요 성공 (isLike=${likeResponse.isLike})")
        likeResponse
    }.onFailure { e ->
        Log.e("PostRepository", "싫어요 에러", e)
    }
}

