package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseTrackerDetail(

    @field:SerializedName("createdAt")
	val createdAt: Long? = null,

    @field:SerializedName("image")
	val image: String? = null,

    @field:SerializedName("locationHistory")
	val locationHistory: LocationHistory? = null,

    @field:SerializedName("tracker_id")
	val trackerId: String? = null,

    @field:SerializedName("createdBy")
	val createdBy: String? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("plateNum")
	val plateNum: String? = null,

    @field:SerializedName("vehicleType")
	val vehicleType: String? = null,

    @field:SerializedName("updatedAt")
	val updatedAt: Long? = null,

    @field:SerializedName("error")
	val error: String? = null
) : Parcelable

@Parcelize
data class LocationHistory(

	@field:SerializedName("2024-06-07T12:00:00Z")
	val jsonMember20240607T120000Z: List<String?>? = null,

	@field:SerializedName("2024-06-05T12:00:00Z")
	val jsonMember20240605T120000Z: List<String?>? = null
) : Parcelable
