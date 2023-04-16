package com.cepotdev.dicory.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.helper.SessionManager
import com.cepotdev.dicory.logic.model.DetailStoriesResponse
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.logic.model.StoriesResponse
import com.cepotdev.dicory.logic.model.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(context: Context): ViewModel() {
    private val _storyItem = MutableLiveData<List<ListStoryItem>>()
    val storyItem: LiveData<List<ListStoryItem>> = _storyItem

    private val _story = MutableLiveData<DetailStoriesResponse>()
    val story: LiveData<DetailStoriesResponse> = _story

    private val sessionManager: SessionManager = SessionManager(context)

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        showStories()
    }

    fun showStories() {
        val client = ApiConfig.getApiService().getAllStories(token = "Bearer ${sessionManager.fetchAuthToken()}")
        client.enqueue(object : Callback<StoriesResponse> {
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
        val client = ApiConfig.getApiService().getDetailStories(token = "Bearer ${sessionManager.fetchAuthToken()}", id)
        client.enqueue(object : Callback<DetailStoriesResponse> {
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