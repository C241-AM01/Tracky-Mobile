package com.megalogic.tracky.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import java.util.Locale

object GeocodingUtils {
    fun reverseGeocode(context: Context, lat: Double, lon: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown Location"
        } catch (e: Exception) {
            Log.e("GeocodingUtils", "Error in reverse geocoding: ${e.message}")
            "Unknown Location"
        }
    }
}
