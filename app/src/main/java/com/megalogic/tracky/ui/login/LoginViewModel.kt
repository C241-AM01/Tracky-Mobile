package com.megalogic.tracky.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megalogic.tracky.data.Repository
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.model.DecodedToken
import com.megalogic.tracky.data.model.ResponseLogin
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel(){

    private val _responseLogin = repository.responseLogin
    val responseLogin: LiveData<ResultState<ResponseLogin>> = _responseLogin

    private val _decodedToken = repository.decodedToken
    val decodedToken: LiveData<ResultState<DecodedToken>> = _decodedToken

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

}