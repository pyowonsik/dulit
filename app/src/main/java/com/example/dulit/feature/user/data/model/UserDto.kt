// feature/user/data/model/UserDto.kt
package com.example.dulit.feature.user.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("socialId")
    val socialId: String,
//
//    @SerializedName("isConnected")  // 👈 추가!
//    val isConnected: Boolean = false
)
