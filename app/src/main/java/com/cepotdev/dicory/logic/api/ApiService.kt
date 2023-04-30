package com.cepotdev.dicory.logic.api

import com.cepotdev.dicory.logic.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun userRegister(@Body userData: UserRequest): Call<UserResponse>

    @GET("stories")
    fun getAllStories(): Call<StoriesResponse>

    @GET("stories")
    fun getStoryLocation(@Query("location") location: Int): Call<StoriesResponse>

    @GET("stories/{id}")
    fun getDetailStories(@Path("id") id: String): Call<DetailStoriesResponse>

    @Multipart
    @POST("stories")
    fun postStories(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<PostingStoriesResponse>
}