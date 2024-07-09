package com.dicoding.utaya.data.response.camera

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponsePredict(

	@field:SerializedName("skinType")
	val skinType: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("skinTypePercentage")
	val skinTypePercentage: String,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem>,

	@field:SerializedName("urlImage")
	val urlImage: String
) : Parcelable