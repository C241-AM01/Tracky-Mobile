package com.megalogic.tracky.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

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

    fun saveRole(role: String) {
        with(sharedPref.edit()) {
            putString("ROLE", role)
            apply()
        }
    }

    fun getRole(): String? {
        return sharedPref.getString("ROLE", null)
    }

    fun saveExp(exp: Long) {
        with(sharedPref.edit()) {
            putLong("EXP", exp)
            apply()
        }
    }

    fun getExp(): Long {
        return sharedPref.getLong("EXP", 0)
    }

    fun clearData() {
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }


}