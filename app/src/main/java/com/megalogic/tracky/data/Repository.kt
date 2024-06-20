package com.megalogic.tracky.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.megalogic.tracky.data.api.ApiService
import com.megalogic.tracky.data.model.AssetListResponse
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.data.model.DecodedToken
import com.megalogic.tracky.data.model.LoginRequest
import com.megalogic.tracky.data.model.ResponseLogin
import com.megalogic.tracky.data.model.TrackersItem
import com.megalogic.tracky.utils.JWTDecoder

class Repository(private val apiService: ApiService, private val context: Context) {

    private val preferenceManager = PreferenceManager(context)

    private val _responseLogin = MutableLiveData<ResultState<ResponseLogin>>()
    val responseLogin: LiveData<ResultState<ResponseLogin>> = _responseLogin

    private val _assetList = MutableLiveData<ResultState<AssetListResponse>>()
    val assetList: LiveData<ResultState<AssetListResponse>> = _assetList

    private val _assetData = MutableLiveData<ResultState<AssetsItem>>()
    val assetData: LiveData<ResultState<AssetsItem>> = _assetData

    private val _trackerData = MutableLiveData<ResultState<TrackersItem>>()
    val trackerData: LiveData<ResultState<TrackersItem>> = _trackerData

    private val _decodedToken = MutableLiveData<ResultState<DecodedToken>>()
    val decodedToken: LiveData<ResultState<DecodedToken>> = _decodedToken

    suspend fun login(email: String, password: String) {
        _responseLogin.postValue(ResultState.Loading)
        val loginRequest = LoginRequest(email, password)

        apiService.login(loginRequest).let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    it.token?.let { it1 -> saveToken(it1) }
                    val decoded = it.token?.let { it1 -> JWTDecoder.decoded(it1) }

                    val uid = decoded?.get("uid")?.toString()
                    val role = decoded?.get("role")?.toString()
                    val email = decoded?.get("email")?.toString()
                    val issuedAt = decoded?.get("iat") as Double
                    val expiration = decoded?.get("exp") as Double

                    when (role) {
                        "admin" -> {
                            preferenceManager.saveRole(role)
                            preferenceManager.saveExp(expiration.toLong())


                        }
                        "pic" -> {
                            preferenceManager.saveRole(role)
                            preferenceManager.saveExp(expiration.toLong())
                        }
                        "user" -> {
                            preferenceManager.saveRole(role)
                            preferenceManager.saveExp(expiration.toLong())
                        }
                    }

                    val decodedToken = DecodedToken(uid, role, expiration.toLong(), issuedAt.toLong(), email)

                    _decodedToken.postValue(ResultState.Success(decodedToken))
                    _responseLogin.postValue(ResultState.Success(it))
                }
            } else {
                _responseLogin.postValue(ResultState.Error(response.message()))
            }
        }
    }

    suspend fun getAssetList() {
        _responseLogin.postValue(ResultState.Loading)
        val token = getToken()
        apiService.getAssetList("Bearer $token").let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    _assetList.postValue(ResultState.Success(it))
                }
            } else {
                _assetList.postValue(ResultState.Error(response.message()))
            }
        }
    }

    suspend fun getAssetDetail(id: Int) {
        _responseLogin.postValue(ResultState.Loading)
        val token = getToken()
        apiService.getAssetDetail("Bearer $token", id).let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    _assetData.postValue(ResultState.Success(it))
                }
            } else {
                _assetData.postValue(ResultState.Error(response.message()))
            }
        }
    }

    suspend fun getTrackerDetail(id: Int) {
        _responseLogin.postValue(ResultState.Loading)
        val token = getToken()
        apiService.getTrackerDetail("Bearer $token", id).let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    _trackerData.postValue(ResultState.Success(it))
                }
            } else {
                _trackerData.postValue(ResultState.Error(response.message()))
            }
        }
    }


    fun saveToken(token: String) {
        preferenceManager.saveToken(token)
    }

    fun getToken(): String? {
        return preferenceManager.getToken()
    }

    fun getRole(): String? {
        return preferenceManager.getRole()
    }

    fun clearData() {
        preferenceManager.clearData()
    }


}