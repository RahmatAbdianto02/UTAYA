package com.dicoding.utaya.data.response.history

import com.google.gson.annotations.SerializedName

data class ResponseHistory(

	@field:SerializedName("historys")
	val historys: List<HistorysItem?>? = null
)

data class RecommendationsItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("urlArticle")
	val urlArticle: String? = null,

	@field:SerializedName("productName")
	val productName: String? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null,

	@field:SerializedName("urlProduct")
	val urlProduct: String? = null
)

data class HistorysItem(

	@field:SerializedName("skinType")
	val skinType: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("skinTypePercentage")
	val skinTypePercentage: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem?>? = null,

	@field:SerializedName("urlImage")
	val urlImage: String? = null
)
