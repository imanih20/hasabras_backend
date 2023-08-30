package com.mohyeddin.store_accountent.data.statistic.remote.dto

import com.google.gson.annotations.SerializedName

data class MarketStatisticResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("monthSale") val monthSale: Int,
    @SerializedName("monthPurchase") val monthPurchase: Int,
    @SerializedName("monthProfit") val monthProfit: Int,
    @SerializedName("monthOwnerSale") val monthOwnerSale: Int,
    @SerializedName("monthOwnerPurchase") val monthOwnerPurchase: Int,
    @SerializedName("monthOwnerProfit") val monthOwnerProfit: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("month") val month: Int
)