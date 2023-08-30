package com.mohyeddin.store_accountent.domain.trade.repository

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.trade.remote.dto.PurchaseRequest
import com.mohyeddin.store_accountent.data.trade.remote.dto.SaleRequest
import com.mohyeddin.store_accountent.data.trade.remote.dto.TradeResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import kotlinx.coroutines.flow.Flow

interface TradeRepository {

    suspend fun getDateTrades(date: String) : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>

    suspend fun getTrades() : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>

    suspend fun addSales(saleList: List<Sale>) : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>

    suspend fun addPurchases(purchaseList: List<Purchase>) : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>

    suspend fun deleteTrade(id: String) : Flow<BaseResult<Trade,WrappedResponse<TradeResponse>>>

//    suspend fun addPurchase(productId: String, purchaseRequest: PurchaseRequest) : Flow<BaseResult<Trade,WrappedResponse<TradeResponse>>>
//
//    suspend fun addSale(productId: String,saleRequest: SaleRequest) : Flow<BaseResult<Trade,WrappedResponse<TradeResponse>>>
//
//    suspend fun getDayPurchases(date: String) : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>
//
//    suspend fun getDaySales(date: String) : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>
//
//    suspend fun getAllPurchases() : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>
//
//    suspend fun getAllSales() : Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>
}