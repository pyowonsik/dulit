package com.example.dulit.feature.post.data.model

import com.google.gson.annotations.SerializedName

/**
 * 게시글 작성자 정보 DTO
 */
data class PostAuthorDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("socialId")
    val socialId: String
)

/**
 * DTO → Domain Model 변환
 */
fun PostAuthorDto.toDomain(): com.example.dulit.feature.post.domain.model.PostAuthor {
    return com.example.dulit.feature.post.domain.model.PostAuthor(
        id = id,
        name = name,
        email = email,
        socialId = socialId
    )
}





