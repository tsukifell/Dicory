package com.cepotdev.dicory.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val context: Context) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _storyItem = MutableLiveData<List<ListStoryItem>>()
    val storyItem: LiveData<List<ListStoryItem>> = _storyItem

    private val _story = MutableLiveData<DetailStoriesResponse>()
    val story: LiveData<DetailStoriesResponse> = _story

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "AuthViewModel"
    }

    init {
        showStories()
    }

    fun userLogin(loginRequest: LoginRequest) {
        apiConfig.getApiService(context).userLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        _loginResponse.postValue(response.body())
                        // Save the user token in session manager
                        response.body()?.loginResult?.token?.let {
                            SessionManager(context).saveAuthToken(it)
                        }
                    } else {
                        _loginResponse.postValue(
                            LoginResponse(
                                error = true,
                                message = "Check your email or password!"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun showStories() {
        apiConfig.getApiService(context).getAllStories()
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        @Suppress("UNCHECKED_CAST")
                        _storyItem.value = response.body()?.listStory as List<ListStoryItem>
                    } else {
                        Log.d(TAG, "onFailure: ${response.message()} ")
                    }
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getDetailStory(id: String) {
        apiConfig.getApiService(context).getDetailStories(id)
            .enqueue(object : Callback<DetailStoriesResponse> {
                override fun onResponse(
                    call: Call<DetailStoriesResponse>,
                    response: Response<DetailStoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "getDetailStory onResponse: successful")
                        _story.value = response.body()
                    } else {
                        Log.d(TAG, "onFailure: ${response.message()} ")
                    }

                    Log.d(TAG, "Story response: ${response.body()?.toString()}")
                }

                override fun onFailure(call: Call<DetailStoriesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun userRegister(userData: UserRequest) {
        apiConfig.getApiService(context).userRegister(userData)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _userResponse.postValue(response.body())
                    } else {
                        _userResponse.postValue(
                            UserResponse(
                                error = true,
                                message = "Failed to register user"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }
}