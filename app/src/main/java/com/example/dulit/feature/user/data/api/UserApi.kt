// feature/user/data/api/UserApi.kt
package com.example.dulit.feature.user.data.api

import com.example.dulit.feature.user.data.model.UserDto
import retrofit2.http.GET

interface UserApi {
    @GET("/user/me")
    suspend fun getMe(): UserDto
}