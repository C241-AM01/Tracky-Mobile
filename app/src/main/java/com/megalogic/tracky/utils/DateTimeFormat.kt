package com.megalogic.tracky.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateTimeFormat {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun formatDate(timestamp: String): String {
        val date = inputFormat.parse(timestamp)
        return dateFormat.format(date!!)
    }

    fun formatTime(timestamp: String): String {
        val date = inputFormat.parse(timestamp)
        return timeFormat.format(date!!)
    }
}