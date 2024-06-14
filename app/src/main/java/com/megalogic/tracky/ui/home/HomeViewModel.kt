package com.megalogic.tracky.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megalogic.tracky.data.Repository
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.model.AssetListResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _assetList = repository.assetList
    val assetList: LiveData<ResultState<AssetListResponse>> = _assetList

    fun getAssetList() {
        viewModelScope.launch {
            repository.getAssetList()
        }
    }

    fun clearData(){
        repository.clearData()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}