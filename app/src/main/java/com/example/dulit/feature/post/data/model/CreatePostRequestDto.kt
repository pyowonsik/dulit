package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 게시글 작성 요청 DTO
 */
data class CreatePostRequestDto(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("filePaths")
    val filePaths: List<String>? = null
)

/**
 * Domain Model → DTO 변환
 */
fun com.example.dulit.feature.post.domain.model.CreatePostRequest.toDto(): CreatePostRequestDto {
    return CreatePostRequestDto(
        title = title,
        description = description,
        filePaths = filePaths
    )
}
