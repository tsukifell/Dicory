package com.cepotdev.dicory.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.data.StoriesRepository
import com.cepotdev.dicory.logic.model.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val storiesRepository: StoriesRepository) : ViewModel() {
    private val _storyItem = MutableLiveData<List<ListStoryItem>>()
    val storyItem: LiveData<List<ListStoryItem>> = _storyItem

    private val _storyLocationItem = MutableLiveData<List<ListStoryItem>>()
    val storyLocationItem: LiveData<List<ListStoryItem>> = _storyLocationItem

    private val _isMainLoading = MutableLiveData<Boolean>()
    val isMainLoading: LiveData<Boolean> = _isMainLoading

    private val apiConfig = ApiConfig()

    companion object {
        private const val TAG = "MainViewModel"
    }

    val stories: LiveData<PagingData<ListStoryItem>> =
        storiesRepository.getAllStories().cachedIn(viewModelScope)

//    fun showLocationStories() {
//        apiConfig.getApiService(getApplication()).getStoryLocation(1)
//            .enqueue(object : Callback<StoriesResponse> {
//                override fun onResponse(
//                    call: Call<StoriesResponse>,
//                    response: Response<StoriesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        @Suppress("UNCHECKED_CAST")
//                        _storyLocationItem.value = response.body()?.listStory as List<ListStoryItem>
//                    } else {
//                        Log.d(TAG, "onFailure: ${response.message()} ")
//                    }
//                    Log.d("MapsActivity", "showLocationStories() called")
//                }
//
//                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
//                    Log.e(TAG, "onFailure: ${t.message}")
//                }
//
//            })
//    }

}