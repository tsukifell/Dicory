package com.cepotdev.dicory.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    // Remove it when submitting!

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _storyItem = MutableLiveData<List<ListStoryItem>>()
    val storyItem: LiveData<List<ListStoryItem>> = _storyItem

    private val _story = MutableLiveData<DetailStoriesResponse>()
    val story: LiveData<DetailStoriesResponse> = _story

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    private val _postingResponse = MutableLiveData<PostingStoriesResponse>()
    val postingResponse: LiveData<PostingStoriesResponse> = _postingResponse

    private val _isRegisterLoading = MutableLiveData<Boolean>()
    val isRegisterLoading: LiveData<Boolean> = _isRegisterLoading

    private val _isLoginLoading = MutableLiveData<Boolean>()
    val isLoginLoading: LiveData<Boolean> = _isLoginLoading

    private val _isMainLoading = MutableLiveData<Boolean>()
    val isMainLoading: LiveData<Boolean> = _isMainLoading

    private val _isDetailLoading = MutableLiveData<Boolean>()
    val isDetailLoading: LiveData<Boolean> = _isDetailLoading

    private val _isPostLoading = MutableLiveData<Boolean>()
    val isPostLoading: LiveData<Boolean> = _isPostLoading

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "AuthViewModel"
    }

    init {
        showStories()
    }

    fun userLogin(loginRequest: LoginRequest) {
        _isLoginLoading.value = true
        apiConfig.getApiService(getApplication()).userLogin(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isLoginLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _loginResponse.postValue(response.body())
                        response.body()?.loginResult?.token?.let {
                            SessionManager(getApplication()).saveAuthToken(it)
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
        _isMainLoading.value = true
        apiConfig.getApiService(getApplication()).getAllStories()
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    _isMainLoading.value = false
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
        _isDetailLoading.value = true
        apiConfig.getApiService(getApplication()).getDetailStories(id)
            .enqueue(object : Callback<DetailStoriesResponse> {
                override fun onResponse(
                    call: Call<DetailStoriesResponse>,
                    response: Response<DetailStoriesResponse>
                ) {
                    _isDetailLoading.value = false
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
        _isRegisterLoading.value = true
        apiConfig.getApiService(getApplication()).userRegister(userData)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isRegisterLoading.value = false
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

    fun postStories(imageFile: MultipartBody.Part, description: RequestBody) {
        _isPostLoading.value = true
        apiConfig.getApiService(getApplication()).postStories(imageFile, description)
            .enqueue(object : Callback<PostingStoriesResponse> {
                override fun onResponse(
                    call: Call<PostingStoriesResponse>,
                    response: Response<PostingStoriesResponse>
                ) {
                    _isPostLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _postingResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<PostingStoriesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }
}