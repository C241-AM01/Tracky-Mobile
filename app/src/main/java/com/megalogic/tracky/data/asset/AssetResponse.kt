package com.megalogic.tracky.data.asset

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asset(
    val title: String,
    val image: String,
    val description: String,
    val price: Double,
    val date: String
) : Parcelable
