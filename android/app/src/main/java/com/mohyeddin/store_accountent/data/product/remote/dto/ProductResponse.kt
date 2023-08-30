package com.mohyeddin.store_accountent.data.product.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("price") val price: Int,
    @SerializedName("profit") val profit: Int,
    @SerializedName("unit") val unit: String,
    @SerializedName("owner") val ownerId: String,
)