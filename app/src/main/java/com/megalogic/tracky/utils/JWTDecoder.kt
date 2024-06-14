package com.megalogic.tracky.utils

import android.util.Base64
import com.google.gson.Gson
import java.io.UnsupportedEncodingException


object JWTDecoder {
    @Throws(Exception::class)
    fun decoded(JWTEncoded: String): Map<String, Any> {
        return try {
            val split = JWTEncoded.split(".")
            val body = getJson(split[1])
            val gson = Gson()
            gson.fromJson(body, Map::class.java) as Map<String, Any>
        } catch (e: UnsupportedEncodingException) {
            emptyMap()
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, charset("UTF-8"))
    }
}