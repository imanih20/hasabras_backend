package com.mohyeddin.store_accountent.data.trade.remote

import com.mohyeddin.store_accountent.data.trade.remote.dto.PurchaseRequest
import com.mohyeddin.store_accountent.data.trade.remote.dto.SaleRequest
import com.mohyeddin.store_accountent.data.trade.remote.dto.TradeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TradeService {
    @DELETE("trade/{id}")
    suspend fun deleteTrade(@Path("id") id: String) : Response<TradeResponse>

    @GET("trade/{date}")
    suspend fun getDateTrades(@Path("date")date: String) :Response<List<TradeResponse>>

    @GET("trade/")
    suspend fun getTrades() : Response<List<TradeResponse>>

    @POST("trade/purchase/")
    suspend fun addPurchases(@Body reqList: List<PurchaseRequest>) : Response<List<TradeResponse>>

    @POST("trade/sale/")
    suspend fun addSales(@Body reqList: List<SaleRequest>) : Response<List<TradeResponse>>

//    @POST("trade/purchase/{id}")
//    suspend fun addPurchase(@Path("id") productId: String ,@Body purchaseRequest: PurchaseRequest) : Response<TradeResponse>
//
//    @POST("trade/sale/{id}")
//    suspend fun addSale(@Path("id") productId: String,@Body saleRequest: SaleRequest) : Response<TradeResponse>

//    @GET("trade/purchase/")
//    suspend fun getAllPurchases() : Response<List<TradeResponse>>
//
//    @GET("trade/purchase/{date}")
//    suspend fun getDayPurchases(@Path("date") date: String) : Response<List<TradeResponse>>
//
//    @GET("trade/sale/")
//    suspend fun getAllSales() : Response<List<TradeResponse>>
//
//    @GET("trade/sale/{date}")
//    suspend fun getDaySales(@Path("date") date: String) : Response<List<TradeResponse>>
}