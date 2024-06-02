package com.megalogic.tracky.utils

import java.text.NumberFormat
import java.util.Locale

object PriceFormat {
    fun getFormattedInitialPrice(initialPrice: Int): String {
        return formatPrice(initialPrice)
    }

    fun getFormattedFinalPrice(finalPrice: Int): String {
        return formatPrice(finalPrice)
    }

    private fun formatPrice(price: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return format.format(price)
    }
}