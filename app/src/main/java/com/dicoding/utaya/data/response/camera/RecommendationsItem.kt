package com.dicoding.utaya.data.response.camera

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendationsItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("urlArticle")
	val urlArticle: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("urlImage")
	val urlImage: String,

	@field:SerializedName("urlProduct")
	val urlProduct: String
) : Parcelable