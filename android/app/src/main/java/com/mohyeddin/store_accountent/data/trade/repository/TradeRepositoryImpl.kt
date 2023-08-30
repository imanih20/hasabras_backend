package com.mohyeddin.store_accountent.data.trade.repository

import android.content.Context
import android.util.Log
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.trade.remote.TradeService
import com.mohyeddin.store_accountent.data.trade.remote.dto.PurchaseRequest
import com.mohyeddin.store_accountent.data.trade.remote.dto.SaleRequest
import com.mohyeddin.store_accountent.data.trade.remote.dto.TradeResponse
import com.mohyeddin.store_accountent.data.trade.utils.TradeResult
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import com.mohyeddin.store_accountent.domain.trade.repository.TradeRepository
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import com.patrykandpatrick.vico.core.extension.getFieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class TradeRepositoryImpl(private val service: TradeService,private val context: Context) : TradeRepository {
    private val result = TradeResult()
    override suspend fun getDateTrades(date: String): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
        val response = service.getDateTrades(date.withEnglishNumber())
        return result.getListResult(response)
    }

    override suspend fun getTrades(): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
        val response = service.getTrades()
        return result.getListResult(response)
    }

    override suspend fun addSales(saleList: List<Sale>): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
        var response : Response<List<TradeResponse>>? = null
        if(checkInternet(context)){
            response = service.addSales(saleList.map {
                SaleRequest(it.productId,it.quantity,it.productPrice,it.date.withEnglishNumber())
            })
        }
        return result.getListResult(response)
    }

    override suspend fun addPurchases(purchaseList: List<Purchase>): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
        var response : Response<List<TradeResponse>>? = null
        if (checkInternet(context)){
            response = service.addPurchases(purchaseList.map {
                PurchaseRequest(
                    productId = it.pid,
                    title = it.title,
                    quantity = it.quantity,
                    totalPrice = it.totalPrice,
                    date = it.date.withEnglishNumber(),
                    ownerId = it.ownerId,
                    salePrice = it.salePrice,
                    unit = it.unit
                )
            })
        }
        return result.getListResult(response)
    }

    override suspend fun deleteTrade(id: String): Flow<BaseResult<Trade, WrappedResponse<TradeResponse>>> {
        var response : Response<TradeResponse>? = null
        if (checkInternet(context)){
            response = service.deleteTrade(id)
        }
        return result.getResult(response)
    }

//    override suspend fun addPurchase(
//        productId: String,
//        purchaseRequest: PurchaseRequest
//    ): Flow<BaseResult<Trade, WrappedResponse<TradeResponse>>> {
//        val response = service.addPurchase(productId, purchaseRequest)
//        return result.getResult(response)
//    }
//
//    override suspend fun addSale(
//        productId: String,
//        saleRequest: SaleRequest
//    ): Flow<BaseResult<Trade, WrappedResponse<TradeResponse>>> {
//        val response = service.addSale(productId, saleRequest)
//        return result.getResult(response)
//    }
//
//    override suspend fun getDayPurchases(date: String): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
//        val response = service.getDayPurchases(date)
//        return result.getListResult(response)
//    }
//
//    override suspend fun getDaySales(date: String): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
//        val response = service.getDaySales(date)
//        return result.getListResult(response)
//    }
//
//    override suspend fun getAllPurchases(): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
//        val response = service.getAllPurchases()
//        return result.getListResult(response)
//    }
//
//    override suspend fun getAllSales(): Flow<BaseResult<List<Trade>, WrappedListResponse<TradeResponse>>> {
//        val response = service.getAllSales()
//        return result.getListResult(response)
//    }
}