package com.mohyeddin.store_accountent.domain.statistic.models

data class MarketStatistic(
    val id: String,
    val marketSale: Int,
    val marketPurchase: Int,
    val marketProfit: Int,
    val ownerSale: Int,
    val ownerPurchase: Int,
    val ownerProfit: Int,
    val year: Int,
    val month: Int
)