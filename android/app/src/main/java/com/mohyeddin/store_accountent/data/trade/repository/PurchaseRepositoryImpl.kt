package com.mohyeddin.store_accountent.data.trade.repository

import com.mohyeddin.store_accountent.data.trade.database.PurchaseDao
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.repository.PurchaseRepository
import kotlinx.coroutines.flow.Flow

class PurchaseRepositoryImpl(private val purchaseDao: PurchaseDao) : PurchaseRepository{
    override suspend fun insert(purchase: Purchase) {
        purchaseDao.insert(purchase)
    }

    override fun getAll(): Flow<List<Purchase>> {
        return purchaseDao.getAll()
    }

    override suspend fun delete(vararg purchase: Purchase) {
        purchaseDao.delete(*purchase)
    }

    override suspend fun deleteAll() {
        purchaseDao.deleteAll()
    }

    override suspend fun update(purchase: Purchase) {
        purchaseDao.update(purchase)
    }

    override fun getCount() : Flow<Int>{
        return purchaseDao.countPurchases()
    }
}