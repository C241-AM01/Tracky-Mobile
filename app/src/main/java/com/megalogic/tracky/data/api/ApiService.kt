package com.megalogic.tracky.data.api

import com.megalogic.tracky.data.asset.AssetRequest
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.model.AddAssetResponse
import com.megalogic.tracky.data.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

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

    @POST("asset")
    fun addAsset(@Body assetRequest: AssetRequest): Call<Void>

    @GET("asset/{id}")
    suspend fun getAssetDetail(@Path("id") id: Int): Response<AssetResponse>
}