package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssetListResponse(

	@field:SerializedName("assets")
	val assets: List<AssetsItem?>? = null
) : Parcelable

@Parcelize
data class AssetsItem(

	@field:SerializedName("purchaseDate")
	val purchaseDate: String? = null,

	@field:SerializedName("originalPrice")
	val originalPrice: String? = null,

	@field:SerializedName("currentPrice")
	val currentPrice: Double? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("editApproved")
	val editApproved: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: Long? = null,

	@field:SerializedName("qrCode")
	val qrCode: String? = null,

	@field:SerializedName("trackerId")
	val trackerId: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("imageURL")
	val imageURL: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("depreciationRate")
	val depreciationRate: String? = null,

	@field:SerializedName("depreciationValue")
	val depreciationValue: String? = null,

	@field:SerializedName("editRequested")
	val editRequested: Boolean? = null,

	@field:SerializedName("error")
	val error: String? = null
) : Parcelable
