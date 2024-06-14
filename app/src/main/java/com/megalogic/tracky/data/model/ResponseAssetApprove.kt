package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseAssetApprove(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("updates")
	val updates: Updates? = null,

	@field:SerializedName("error")
	val error: String? = null
) : Parcelable

@Parcelize
data class Updates(

	@field:SerializedName("editRequestedBy")
	val editRequestedBy: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("editRequestedAt")
	val editRequestedAt: Long? = null,

	@field:SerializedName("editRequested")
	val editRequested: Boolean? = null
) : Parcelable
