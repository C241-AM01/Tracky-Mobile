package com.megalogic.tracky.ui.livetracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveTrackingViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is live tracking Fragment"
    }
    val text: LiveData<String> = _text
}