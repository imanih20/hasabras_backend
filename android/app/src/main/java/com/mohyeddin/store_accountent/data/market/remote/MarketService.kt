package com.mohyeddin.store_accountent.data.market.remote

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketRequest
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MarketService {
    @POST("market/")
    suspend fun insertMarket(@Body marketRequest: MarketRequest) : Response<MarketResponse>

    @PUT("market/")
    suspend fun updateMarket(@Body marketRequest: MarketRequest) : Response<MarketResponse>

//    @DELETE("market/{id}")
//    suspend fun deleteMarket(@Path("id") id: String) : Response<MarketResponse>

    @GET("market/")
    suspend fun getMarket() : Response<MarketResponse>
}