package com.megalogic.tracky.data.asset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.util.Locale

@Parcelize
data class AssetResponse(
    val trackerId: Int,
    val title: String,
    val image: String,
    val description: String,
    val depreciation: Int,
    val initialPrice: Int,
    val finalPrice: Int,
    val date: String
) : Parcelable
