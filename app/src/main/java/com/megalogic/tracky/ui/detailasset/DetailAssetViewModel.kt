package com.megalogic.tracky.ui.detailasset

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megalogic.tracky.data.Repository
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.model.AssetsItem
import kotlinx.coroutines.launch

class DetailAssetViewModel(private val repository: Repository) : ViewModel() {

    private val _assetData = repository.assetData
    val assetData: LiveData<ResultState<AssetsItem>> = _assetData

    fun getAssetDetail(id: Int) {
        viewModelScope.launch {
            repository.getAssetDetail(id)
        }
    }
}