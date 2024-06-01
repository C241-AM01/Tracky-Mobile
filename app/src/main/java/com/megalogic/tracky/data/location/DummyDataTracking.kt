package com.megalogic.tracky.data.location

import com.google.android.gms.maps.model.LatLng

object DummyDataTracking {
    val trackingData = listOf(
        mapOf("tracker_id" to 1, "lat" to "-6.9217570343291035", "lon" to "107.61199905662174", "timestamp" to "2024-03-26 17:19:57"),
        mapOf("tracker_id" to 1, "lat" to "-6.923024458828421", "lon" to "107.61185958175302", "timestamp" to "2024-03-26 17:19:58"),
        mapOf("tracker_id" to 1, "lat" to "-6.924716918488874", "lon" to "107.61166254979153", "timestamp" to "2024-03-26 17:19:59"),
        mapOf("tracker_id" to 1, "lat" to "-6.924806478760358", "lon" to "107.61326843707313", "timestamp" to "2024-03-26 17:20:00"),
        mapOf("tracker_id" to 1, "lat" to "-6.925934936724146", "lon" to "107.61328648075047", "timestamp" to "2024-03-26 17:20:01"),
        mapOf("tracker_id" to 1, "lat" to "-6.92611854375312", "lon" to "107.61269918860873", "timestamp" to "2024-03-26 17:20:05"),
        mapOf("tracker_id" to 1, "lat" to "-6.926218187895285", "lon" to "107.61259881195288", "timestamp" to "2024-03-26 17:20:10"),
        mapOf("tracker_id" to 1, "lat" to "-6.9266773471166205", "lon" to "107.61297359483586", "timestamp" to "2024-03-26 17:20:11"),

    )

    val locationHistory: List<LatLng>
        get() = trackingData.map {
            LatLng(it["lat"].toString().toDouble(), it["lon"]!!.toString().toDouble())
        }
}



