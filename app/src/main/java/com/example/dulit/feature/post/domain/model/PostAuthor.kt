package com.example.dulit.feature.post.domain.model

/**
 * 게시글 작성자 정보 Domain Model
 */
data class PostAuthor(
    val id: Int,
    val name: String,
    val email: String,
    val socialId: String
)



