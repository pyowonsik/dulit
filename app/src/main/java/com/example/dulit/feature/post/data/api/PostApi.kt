package com.example.dulit.feature.post.data.api

import com.example.dulit.feature.post.data.model.CreatePostRequestDto
import com.example.dulit.feature.post.data.model.PostLikeResponseDto
import com.example.dulit.feature.post.data.model.PostResponseDto
import com.example.dulit.feature.post.data.model.UpdatePostRequestDto
import retrofit2.Response
import retrofit2.http.*

interface PostApi {

    /**
     * 게시글 작성
     * POST /post
     */
    @POST("/post")
    suspend fun createPost(
        @Body request: CreatePostRequestDto
    ): Response<PostResponseDto>

    /**
     * 게시글 전체 조회 (페이지네이션)
     * GET /post?page=1&take=10&order=ASC
     */
    @GET("/post")
    suspend fun findAllPosts(
        @Query("page") page: Int? = null,
        @Query("take") take: Int? = null,
        @Query("order") order: String? = null
    ): Response<List<PostResponseDto>>

    /**
     * 게시글 단건 조회
     * GET /post/:postId
     */
    @GET("/post/{postId}")
    suspend fun findOnePost(
        @Path("postId") postId: Int
    ): Response<PostResponseDto>

    /**
     * 게시글 수정
     * PATCH /post/:postId
     */
    @PATCH("/post/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Int,
        @Body request: UpdatePostRequestDto
    ): Response<PostResponseDto>

    /**
     * 게시글 삭제
     * DELETE /post/:postId
     * @return 삭제된 postId (number)
     */
    @DELETE("/post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Int
    ): Response<Int>

    /**
     * 게시글 좋아요
     * POST /post/:postId/like
     * @return { isLike: boolean | null }
     */
    @POST("/post/{postId}/like")
    suspend fun likePost(
        @Path("postId") postId: Int
    ): Response<PostLikeResponseDto>

    /**
     * 게시글 싫어요
     * POST /post/:postId/dislike
     * @return { isLike: boolean | null }
     */
    @POST("/post/{postId}/dislike")
    suspend fun dislikePost(
        @Path("postId") postId: Int
    ): Response<PostLikeResponseDto>
}
