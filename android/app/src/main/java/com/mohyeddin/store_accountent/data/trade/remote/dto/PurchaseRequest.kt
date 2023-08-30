package com.mohyeddin.store_accountent.data.trade.remote.dto

import com.google.gson.annotations.SerializedName

data class PurchaseRequest(
    @SerializedName("productId") val productId: String,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("totalPrice") val totalPrice: Int,
    @SerializedName("date") val date: String,
    @SerializedName("ownerId") val ownerId: String,
    @SerializedName("salePrice") val salePrice: Int,
    @SerializedName("unit") val unit: String,
)