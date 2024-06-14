package com.megalogic.tracky.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseAssetUpdateRequest(

	@field:SerializedName("editRequestedBy")
	val editRequestedBy: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("editRequestedAt")
	val editRequestedAt: EditRequestedAt? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("editRequested")
	val editRequested: Boolean? = null,

	@field:SerializedName("error")
	val error: String? = null
) : Parcelable

@Parcelize
data class EditRequestedAt(

	@field:SerializedName(".sv")
	val sv: String? = null
) : Parcelable
