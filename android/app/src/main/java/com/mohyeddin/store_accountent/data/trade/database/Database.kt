package com.mohyeddin.store_accountent.data.trade.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.model.Sale

@Database(entities = [Purchase::class, Sale::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun saleDao() : SaleDao
    abstract fun purchaseDao() : PurchaseDao
}