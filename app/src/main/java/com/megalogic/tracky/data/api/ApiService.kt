package com.megalogic.tracky.data.api

import com.megalogic.tracky.data.asset.AssetRequest
import com.megalogic.tracky.data.asset.AssetResponse
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.POST

interface ApiService {
    @POST("asset")
    fun addAsset(@Body assetRequest: AssetRequest): Call<Void>

    @GET("asset/{id}")
    suspend fun getAssetDetail(@Path("id") id: Int): Response<AssetResponse>
}