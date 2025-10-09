// feature/user/data/model/UserDto.kt
package com.example.dulit.feature.user.data.model

import com.example.dulit.feature.user.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("socialId")
    val socialId: String
)

// 👇 DTO → Domain 변환 함수
fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        socialId = socialId
    )
}