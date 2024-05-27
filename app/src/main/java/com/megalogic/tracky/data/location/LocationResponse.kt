package com.megalogic.tracky.data.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationResponse(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
):Parcelable
