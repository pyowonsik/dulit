package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 게시글 삭제 응답 DTO
 * 백엔드는 삭제된 postId를 반환
 */
data class DeletePostResponseDto(
    @SerializedName("id")
    val id: Int? = null
)

/**
 * DTO → Domain Model 변환
 */
fun DeletePostResponseDto.toDomain(): com.example.dulit.feature.post.domain.model.DeletePostResponse {
    return com.example.dulit.feature.post.domain.model.DeletePostResponse(
        deletedPostId = id ?: 0
    )
}





