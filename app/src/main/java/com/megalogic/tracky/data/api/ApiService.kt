package com.megalogic.tracky.data.api

import com.megalogic.tracky.data.asset.AssetRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("asset")
    fun addAsset(@Body assetRequest: AssetRequest): Call<Void>
}