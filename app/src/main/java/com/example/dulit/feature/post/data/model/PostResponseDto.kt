package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 게시글 응답 DTO
 */
data class PostResponseDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("filePaths")
    val filePaths: List<String>?,
    
    @SerializedName("author")
    val author: PostAuthorDto,
    
    @SerializedName("couple")
    val couple: PostCoupleDto?,
    
    @SerializedName("createdAt")
    val createdAt: String,
    
    @SerializedName("updatedAt")
    val updatedAt: String
)

/**
 * DTO → Domain Model 변환
 */
fun PostResponseDto.toDomain(): com.example.dulit.feature.post.domain.model.Post {
    return com.example.dulit.feature.post.domain.model.Post(
        id = id,
        title = title,
        description = description,
        filePaths = filePaths ?: emptyList(),
        author = author.toDomain(),  // ⭐ 확장 함수 사용
        couple = couple?.toDomain(),  // ⭐ 확장 함수 사용
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
