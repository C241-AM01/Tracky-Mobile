package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TrackerListResponse(
    @field:SerializedName("trackers")
    val assets: List<TrackersItem?>? = null
) : Parcelable

@Parcelize
data class TrackersItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("createdBy")
    val createdBy: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("locationHistory")
    val locationHistory: List<LocationHistoryItem>? = null,

    @field:SerializedName("mobile")
    val mobile: Boolean? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("plateNum")
    val plateNum: String? = null,

    @field:SerializedName("tracker_id")
    val trackerId: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("vehicleType")
    val vehicleType: String? = null
) : Parcelable

@Parcelize
data class LocationHistoryItem(
    @field:SerializedName("point")
    val point: Int? = null,

    @field:SerializedName("lat")
    val latitude: String? = null,

    @field:SerializedName("long")
    val longitude: String? = null,

    @field:SerializedName("time")
    val time: String? = null
) : Parcelable
