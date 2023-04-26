package com.cepotdev.dicory.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.PostingStoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val _postingResponse = MutableLiveData<PostingStoriesResponse>()
    val postingResponse: LiveData<PostingStoriesResponse> = _postingResponse

    private val _isPostLoading = MutableLiveData<Boolean>()
    val isPostLoading: LiveData<Boolean> = _isPostLoading

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "PostViewModel"
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