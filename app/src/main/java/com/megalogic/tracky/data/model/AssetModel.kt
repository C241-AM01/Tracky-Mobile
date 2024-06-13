package com.megalogic.tracky.data.model

data class AddAssetResponse(
    val id: String,
    val name: String,
    val description: String,
    val depreciationRate: String,
    val depreciationValue: String,
    val purchaseDate: String,
    val originalPrice: String,
    val trackerId: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String
)

