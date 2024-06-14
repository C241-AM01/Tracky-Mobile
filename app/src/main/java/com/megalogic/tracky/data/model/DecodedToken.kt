package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class DecodedToken(
	val uid: String? = null,
	val role: String? = null,
	val exp: Long? = null,
	val iat: Long? = null,
	val email: String? = null
) : Parcelable
