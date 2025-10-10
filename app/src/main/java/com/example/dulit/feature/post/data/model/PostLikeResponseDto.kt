package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 좋아요/싫어요 응답 DTO
 */
data class PostLikeResponseDto(
    @SerializedName("isLike")
    val isLike: Boolean?
)

/**
 * DTO → Domain Model 변환
 */
fun PostLikeResponseDto.toDomain(): com.example.dulit.feature.post.domain.model.PostLikeResponse {
    return com.example.dulit.feature.post.domain.model.PostLikeResponse(
        isLike = isLike
    )
}

