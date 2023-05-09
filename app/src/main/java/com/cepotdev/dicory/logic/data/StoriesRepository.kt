package com.cepotdev.dicory.logic.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cepotdev.dicory.logic.api.ApiService
import com.cepotdev.dicory.logic.model.ListStoryItem

class StoriesRepository(private val apiService: ApiService) {
    private val _stories = MutableLiveData<List<ListStoryItem?>>()
    val stories: LiveData<List<ListStoryItem?>> = _stories

    suspend fun getAllStories(){
        try{
            val resp = apiService.getAllStories(2,6).listStory
            _stories.postValue(resp!!)
        } catch (ex: Exception){
            Log.e("StoriesRepository", "Failed to fetched the stories!")
        }
    }
}