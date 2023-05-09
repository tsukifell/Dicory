package com.cepotdev.dicory.logic.di

import android.content.Context
import com.cepotdev.dicory.logic.api.ApiConfig
import com.cepotdev.dicory.logic.data.StoriesRepository

object Injection {
    fun provideRepository(context: Context): StoriesRepository{
        val apiService = ApiConfig().getApiService(context)
        return StoriesRepository(apiService)
    }
}