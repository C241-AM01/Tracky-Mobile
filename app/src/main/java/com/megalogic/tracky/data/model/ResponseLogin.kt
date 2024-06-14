package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ResponseLogin(

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)
