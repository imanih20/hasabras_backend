package com.mohyeddin.store_accountent.domain.trade.repository

import com.mohyeddin.store_accountent.domain.trade.model.Sale
import kotlinx.coroutines.flow.Flow


interface SaleRepository {
    suspend fun insert(sale: Sale)

    fun getAll() : Flow<List<Sale>>


    suspend fun delete(vararg sale: Sale)

    suspend fun deleteAll()

    suspend fun update(sale: Sale)

    fun getCount() : Flow<Int>
}