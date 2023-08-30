package com.mohyeddin.store_accountent.data.trade.remote.dto

import com.google.gson.annotations.SerializedName

data class SaleRequest(
    @SerializedName("productId")val productId: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("totalPrice") val totalPrice: Int,
    @SerializedName("date") val date: String
)