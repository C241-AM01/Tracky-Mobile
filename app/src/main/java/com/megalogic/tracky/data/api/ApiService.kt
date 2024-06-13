package com.megalogic.tracky.data.api

import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.model.AddAssetResponse
import com.megalogic.tracky.data.model.AssetListResponse
import com.megalogic.tracky.data.model.LoginRequest
import com.megalogic.tracky.data.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/login")
    suspend fun userLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("asset")
    suspend fun getAssets(
        @Header("Authorization") token: String
    ): Response<AssetListResponse>

    @Multipart
    @POST("asset")
    suspend fun addAsset(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("depreciationRate") depreciationRate: RequestBody,
        @Part("depreciationValue") depreciationValue: RequestBody,
        @Part("purchaseDate") purchaseDate: RequestBody,
        @Part("originalPrice") originalPrice: RequestBody,
        @Part("trackerId") trackerId: RequestBody,
        @Part image: MultipartBody.Part
    ): AddAssetResponse

    @GET("asset/{id}")
    suspend fun getAssetDetail(@Path("id") id: Int): Response<AssetResponse>
}