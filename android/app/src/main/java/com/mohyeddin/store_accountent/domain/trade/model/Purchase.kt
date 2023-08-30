package com.mohyeddin.store_accountent.domain.trade.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Purchase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pid: String,
    val title: String,
    val quantity: Double,
    val totalPrice: Int,
    val unit: String,
    val salePrice: Int,
    val ownerId: String,
    val ownerName: String,
    val date: String
)