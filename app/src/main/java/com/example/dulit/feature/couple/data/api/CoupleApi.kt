package com.example.dulit.feature.couple.data.api

import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Path


interface CoupleApi {
    @POST("couple/connect/{partnerId}")
    suspend fun connectCouple(
        @Path("partnerId") partnerId: String
    ) : Response<Boolean>
}


