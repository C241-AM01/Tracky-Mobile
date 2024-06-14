package com.megalogic.tracky.data.api

import com.megalogic.tracky.data.model.AssetListResponse
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.model.ResponseTrackerDetail
import com.megalogic.tracky.data.model.AddAssetResponse
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.data.model.LoginRequest
import com.megalogic.tracky.data.model.LoginResponse
import com.megalogic.tracky.data.model.ResponseLogin
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

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<ResponseLogin>


    @GET("/asset")
    suspend fun getAssetList(
        @Header("Authorization") token: String
    ): Response<AssetListResponse>

    @GET("/asset/{id}")
    suspend fun getAssetDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<AssetsItem>

    @GET("/tracker/{id}")
    suspend fun getTrackerDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ResponseTrackerDetail>

    @Multipart
    @POST("assets")
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