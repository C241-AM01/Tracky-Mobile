package com.megalogic.tracky.data.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackingResponse(
    val tracker_id: Int,
    val lat: String,
    val lon: String,
    val timestamp: String,
    val asset_name: String
): Parcelable
