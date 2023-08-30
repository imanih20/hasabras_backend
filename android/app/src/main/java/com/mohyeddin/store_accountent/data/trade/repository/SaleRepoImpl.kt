package com.mohyeddin.store_accountent.data.trade.repository

import com.mohyeddin.store_accountent.data.trade.database.SaleDao
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import com.mohyeddin.store_accountent.domain.trade.repository.SaleRepository
import kotlinx.coroutines.flow.Flow

class SaleRepoImpl(private val saleDao: SaleDao) : SaleRepository {
    override suspend fun insert(sale: Sale) {
        var savedSale = saleDao.getDateSale(sale.productId,sale.date)
        if(savedSale != null) {
            savedSale.quantity += sale.quantity
        }else {
            savedSale = sale
        }
        saleDao.insert(savedSale)
    }

    override fun getAll(): Flow<List<Sale>> {
        return saleDao.getList()
    }


    override suspend fun delete(vararg sale: Sale) {
        saleDao.delete(*sale)
    }

    override suspend fun deleteAll(){
        saleDao.deleteAll()
    }

    override suspend fun update(sale: Sale) {
        saleDao.update(sale)
    }

    override fun getCount() : Flow<Int>{
        return saleDao.countSales()
    }
}