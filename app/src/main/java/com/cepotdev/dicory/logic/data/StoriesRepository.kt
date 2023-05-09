package com.cepotdev.dicory.logic.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.cepotdev.dicory.logic.api.ApiService
import com.cepotdev.dicory.logic.model.ListStoryItem

class StoriesRepository(private val apiService: ApiService) {

    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 33
            ),
            pagingSourceFactory = {
                StoriesPagingSource(apiService)
            }
        ).liveData
    }
}
