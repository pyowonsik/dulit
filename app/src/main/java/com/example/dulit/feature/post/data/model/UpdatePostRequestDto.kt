package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 게시글 수정 요청 DTO (모든 필드 optional)
 */
data class UpdatePostRequestDto(
    @SerializedName("title")
    val title: String? = null,
    
    @SerializedName("description")
    val description: String? = null,
    
    @SerializedName("filePaths")
    val filePaths: List<String>? = null
)

/**
 * Domain Model → DTO 변환
 */
fun com.example.dulit.feature.post.domain.model.UpdatePostRequest.toDto(): UpdatePostRequestDto {
    return UpdatePostRequestDto(
        title = title,
        description = description,
        filePaths = filePaths
    )
}
