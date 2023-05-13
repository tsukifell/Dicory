package com.cepotdev.dicory.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.model.ListStoryItem
import com.cepotdev.dicory.logic.model.StoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    private val _storyLocationItem = MutableLiveData<List<ListStoryItem>>()
    val storyLocationItem: LiveData<List<ListStoryItem>> = _storyLocationItem

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "MapsViewModel"
    }

    fun showLocationStories() {
        apiConfig.getApiService(getApplication()).getStoryLocation(1)
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        @Suppress("UNCHECKED_CAST")
                        _storyLocationItem.value = response.body()?.listStory as List<ListStoryItem>
                    } else {
                        Log.d(TAG, "onFailure: ${response.message()} ")
                    }
                    Log.d("MapsActivity", "showLocationStories() called")
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }
}