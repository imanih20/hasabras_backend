package com.mohyeddin.store_accountent.domain.trade.model

data class Trade(
    val id: String,
    val productName: String,
    val quantity: Double,
    val totalPrice: Int,
    val type: String,
    val unit: String,
    val date: String
)