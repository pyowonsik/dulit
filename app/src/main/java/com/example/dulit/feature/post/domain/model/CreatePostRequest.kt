package com.example.dulit.feature.post.domain.model

/**
 * 게시글 작성 요청 Domain Model
 */
data class CreatePostRequest(
    val title: String,
    val description: String,
    val filePaths: List<String>? = null
)

