package com.example.dulit.feature.post.domain.repository

import com.example.dulit.feature.post.domain.model.CreatePostRequest
import com.example.dulit.feature.post.domain.model.Post
import com.example.dulit.feature.post.domain.model.PostLikeResponse
import com.example.dulit.feature.post.domain.model.UpdatePostRequest

/**
 * Post Repository Interface
 */
interface PostRepository {
    
    /**
     * 게시글 작성
     */
    suspend fun createPost(request: CreatePostRequest): Result<Post>
    
    /**
     * 게시글 전체 조회
     * @param page 페이지 번호 (optional)
     * @param take 가져올 개수 (optional)
     * @param order 정렬 순서 (ASC/DESC, optional)
     */
    suspend fun findAllPosts(
        page: Int? = null,
        take: Int? = null,
        order: String? = null
    ): Result<List<Post>>
    
    /**
     * 게시글 단건 조회
     */
    suspend fun findOnePost(postId: Int): Result<Post>
    
    /**
     * 게시글 수정
     */
    suspend fun updatePost(postId: Int, request: UpdatePostRequest): Result<Post>
    
    /**
     * 게시글 삭제
     * @return 삭제된 postId
     */
    suspend fun deletePost(postId: Int): Result<Int>
    
    /**
     * 게시글 좋아요
     * @return 좋아요 상태 (isLike: true/false/null)
     */
    suspend fun likePost(postId: Int): Result<PostLikeResponse>
    
    /**
     * 게시글 싫어요
     * @return 싫어요 상태 (isLike: true/false/null)
     */
    suspend fun dislikePost(postId: Int): Result<PostLikeResponse>
}

