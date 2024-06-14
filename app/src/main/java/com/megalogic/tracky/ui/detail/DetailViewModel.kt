package com.megalogic.tracky.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megalogic.tracky.data.Repository
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.api.ApiClient
import com.megalogic.tracky.data.api.ApiService
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.AssetRequest
import com.megalogic.tracky.data.model.AssetsItem
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _assetData = repository.assetData
    val assetData: LiveData<ResultState<AssetsItem>> = _assetData

    fun getAssetDetail(id: Int) {
        viewModelScope.launch {
            repository.getAssetDetail(id)
        }
    }
}