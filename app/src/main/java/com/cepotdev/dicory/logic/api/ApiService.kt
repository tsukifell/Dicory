package com.cepotdev.dicory.logic.api

import com.cepotdev.dicory.logic.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun userLogin(@Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun userRegister(@Body userData: UserRequest): Call<UserResponse>

    @GET("stories")
    fun getAllStories(@Header("Authorization") token: String): Call<StoriesResponse>
}