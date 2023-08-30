package com.mohyeddin.store_accountent.domain.trade.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val date: String,
    val productName: String,
    val productQuantity: Double,
    var quantity: Double,
    val productPrice: Int,
    val unit: String,
)