package com.cepotdev.dicory.logic.api

import com.cepotdev.dicory.logic.model.LoginResponse
import com.cepotdev.dicory.logic.model.StoriesResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("stories")
    fun getAllStories(): Call<StoriesResponse>

    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}