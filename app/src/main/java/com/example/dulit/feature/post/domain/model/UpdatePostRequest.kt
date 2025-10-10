package com.example.dulit.feature.post.domain.model

/**
 * 게시글 수정 요청 Domain Model (모든 필드 optional)
 */
data class UpdatePostRequest(
    val title: String? = null,
    val description: String? = null,
    val filePaths: List<String>? = null
)

