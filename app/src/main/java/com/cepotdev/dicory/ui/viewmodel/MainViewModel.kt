package com.cepotdev.dicory.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.DetailStoriesResponse
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.logic.model.StoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val context: Context) : ViewModel() {
    private val _storyItem = MutableLiveData<List<ListStoryItem>>()
    val storyItem: LiveData<List<ListStoryItem>> = _storyItem

    private val _story = MutableLiveData<DetailStoriesResponse>()
    val story: LiveData<DetailStoriesResponse> = _story

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        showStories()
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


}