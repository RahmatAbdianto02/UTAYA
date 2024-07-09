package com.dicoding.utaya.data.response.produk

import com.google.gson.annotations.SerializedName

data class ResponseProdukItem(

	@field:SerializedName("data")
	val data: List<DataTypeSkin>,

	@field:SerializedName("id")
	val id: String
)