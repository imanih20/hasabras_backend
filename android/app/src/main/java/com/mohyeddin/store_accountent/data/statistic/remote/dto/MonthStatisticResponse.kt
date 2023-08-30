package com.mohyeddin.store_accountent.data.statistic.remote.dto

import com.google.gson.annotations.SerializedName

data class MonthStatisticResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("user") val shareholderId: String,
    @SerializedName("totalPurchase") val totalPurchase: Int,
    @SerializedName("totalSale") val totalSale: Int,
    @SerializedName("profit") val totalProfit: Int,
    @SerializedName("isPaid") val isPaid: Boolean,
    @SerializedName("year") val year: Int,
    @SerializedName("month") val month: Int,
)