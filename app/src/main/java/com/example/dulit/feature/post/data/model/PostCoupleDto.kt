package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 게시글 커플 정보 DTO
 */
data class PostCoupleDto(
    @SerializedName("id")
    val id: Int
)

/**
 * DTO → Domain Model 변환
 */
fun PostCoupleDto.toDomain(): com.example.dulit.feature.post.domain.model.PostCouple {
    return com.example.dulit.feature.post.domain.model.PostCouple(
        id = id
    )
}



