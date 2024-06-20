package com.megalogic.tracky.ui.detailasset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megalogic.tracky.data.api.ApiClient
import com.megalogic.tracky.data.api.ApiService
import com.megalogic.tracky.data.asset.AssetResponse
import kotlinx.coroutines.launch

class AdminDetailAssetViewModel : ViewModel() {
    private val _assetDetail = MutableLiveData<AssetResponse>()
    val assetDetail: LiveData<AssetResponse> = _assetDetail
    private val apiService: ApiService = ApiClient.retrofit!!.create(ApiService::class.java)
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchAssetDetail(assetId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getAssetDetail(assetId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _assetDetail.postValue(it)
                    } ?: run {
                        _error.postValue("Response body is empty")
                    }
                } else {
                    _error.postValue("Failed to fetch user detail: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("An error occurred: ${e.message}")
            }
        }
    }
}