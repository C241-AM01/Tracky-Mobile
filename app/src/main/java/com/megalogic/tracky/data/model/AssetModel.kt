package com.megalogic.tracky.data.model

import java.io.Serializable

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

data class AssetListResponse(
    val assets: List<AssetResponse>
)

data class AssetResponse(
    val id: String,
    val createdAt: Long,
    val createdBy: String,
    val currentPrice: Int,
    val depreciationRate: String,
    val depreciationValue: String,
    val description: String,
    val editApproved: Boolean,
    val editRequested: Boolean,
    val imageURL: String,
    val name: String,
    val originalPrice: String,
    val purchaseDate: String,
    val qrCode: String,
    val trackerId: String
) : Serializable


