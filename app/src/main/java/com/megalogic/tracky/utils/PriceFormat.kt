package com.megalogic.tracky.utils

import java.text.NumberFormat
import java.util.Locale

object PriceFormat {
    fun getFormattedPrice(price: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return format.format(price)
    }

    fun getFormattedDepreciation(depreciation: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return "-${format.format(depreciation)}/day"
    }
}