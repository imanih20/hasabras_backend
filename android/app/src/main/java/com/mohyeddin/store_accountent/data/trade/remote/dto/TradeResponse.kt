package com.mohyeddin.store_accountent.data.trade.remote.dto

import com.google.gson.annotations.SerializedName

data class TradeResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("product") val productName: String,
    @SerializedName("type") val type: String,
    @SerializedName("unit") val unit: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("totalPrice") val totalPrice: Int,
    @SerializedName("date") val date: String
)