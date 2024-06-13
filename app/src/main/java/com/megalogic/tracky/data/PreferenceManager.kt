package com.megalogic.tracky.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        with(sharedPref.edit()) {
            putString("AUTH_TOKEN", token)
            apply()
        }
    }

    fun getToken(): String? {
        return sharedPref.getString("AUTH_TOKEN", null)
    }

    fun clearToken() {
        with(sharedPref.edit()) {
            remove("AUTH_TOKEN")
            apply()
        }
    }
}
