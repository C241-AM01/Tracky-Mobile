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
    val initialPrice: Int,
    val finalPrice: Int,
    val date: String
) : Parcelable {

    fun getFormattedInitialPrice(): String {
        return formatPrice(initialPrice)
    }

    fun getFormattedFinalPrice(): String {
        return formatPrice(finalPrice)
    }

    private fun formatPrice(price: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return format.format(price)
    }
}
