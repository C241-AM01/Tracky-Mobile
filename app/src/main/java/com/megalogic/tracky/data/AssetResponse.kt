package com.megalogic.tracky.data

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
