package com.cepotdev.dicory.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cepotdev.dicory.logic.data.StoriesRepository
import com.cepotdev.dicory.logic.model.ListStoryItem

class MainViewModel(storiesRepository: StoriesRepository) : ViewModel() {
    private val _isMainLoading = MutableLiveData<Boolean>()
    val isMainLoading: LiveData<Boolean> = _isMainLoading

    val stories: LiveData<PagingData<ListStoryItem>> =
        storiesRepository.getAllStories().cachedIn(viewModelScope)

    fun setMainLoading(loading: Boolean) {
        _isMainLoading.value = loading
    }
}