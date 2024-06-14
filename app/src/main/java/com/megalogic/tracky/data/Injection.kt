package com.megalogic.tracky.data

import android.content.Context
import com.megalogic.tracky.data.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService, context)
    }
}