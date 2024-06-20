package com.megalogic.tracky.ui.detailtracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megalogic.tracky.data.Repository
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.data.model.TrackersItem
import kotlinx.coroutines.launch

class DetailTrackerViewModel(private val repository: Repository) : ViewModel() {

    private val _trackerData = repository.trackerData
    val trackerData: LiveData<ResultState<TrackersItem>> = _trackerData

    fun getTrackerDetail(id: Int) {
        viewModelScope.launch {
            repository.getTrackerDetail(id)
        }
    }
}