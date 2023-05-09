package com.cepotdev.dicory.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cepotdev.dicory.logic.data.StoriesRepository
import com.cepotdev.dicory.logic.model.ListStoryItem

class MainViewModel(storiesRepository: StoriesRepository) : ViewModel() {
    val stories: LiveData<PagingData<ListStoryItem>> =
        storiesRepository.getAllStories().cachedIn(viewModelScope)
}