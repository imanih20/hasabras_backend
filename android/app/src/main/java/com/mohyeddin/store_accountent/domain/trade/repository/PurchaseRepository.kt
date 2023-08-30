package com.mohyeddin.store_accountent.domain.trade.repository

import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {
    suspend fun insert(purchase: Purchase)

    fun getAll() : Flow<List<Purchase>>

    suspend fun delete(vararg purchase: Purchase)

    suspend fun deleteAll()

    suspend fun update(purchase: Purchase)

    fun getCount() : Flow<Int>
}