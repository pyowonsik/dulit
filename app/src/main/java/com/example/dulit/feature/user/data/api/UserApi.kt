package com.example.dulit.feature.user.data.api

//import com.example.dulit.feature.user.data.model.ConnectCoupleResponse
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path


interface UserApi {
    @POST("couple/connect/{partnerId}")
    suspend fun connectCouple(
        @Path("partnerId") partnerId: String
    ) : Response<Boolean>
}


