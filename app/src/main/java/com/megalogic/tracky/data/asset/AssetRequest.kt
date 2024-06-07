package com.megalogic.tracky.data.asset

data class AssetRequest(
    val trackerid: Int,
    val name: String,
    val description: String,
    val purchaseDate: String,
    val depreciationRate: String,
    val depreciationValue: String,
    val originalPrice: String,
    val image: String
)