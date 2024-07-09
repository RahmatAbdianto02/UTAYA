package com.dicoding.utaya.data.response.produk

import com.google.gson.annotations.SerializedName

data class TestResponseProdukItem(
	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(
	@field:SerializedName("skinType")
	val skinType: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem?>? = null
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
