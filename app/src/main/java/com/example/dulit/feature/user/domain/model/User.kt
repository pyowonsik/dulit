// feature/user/domain/model/User.kt
package com.example.dulit.feature.user.domain.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val socialId : String,
    val coupleId: Long?
)