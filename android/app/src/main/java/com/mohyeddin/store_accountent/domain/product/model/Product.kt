package com.mohyeddin.store_accountent.domain.product.model

data class Product(
    val id: String,
    val title: String,
    val price: Int,
    val quantity: Double,
    val profit: Int,
    val ownerId: String,
    val unit: String = "n"
)