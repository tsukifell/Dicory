package com.cepotdev.dicory.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.DetailStoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _isDetailLoading = MutableLiveData<Boolean>()
    val isDetailLoading: LiveData<Boolean> = _isDetailLoading

    private val _story = MutableLiveData<DetailStoriesResponse>()
    val story: LiveData<DetailStoriesResponse> = _story

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "DetailViewModel"
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
}