package com.mohyeddin.store_accountent.data.statistic.remote

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MarketStatisticResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticRequest
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StatisticService {
    @GET("statistic/market/{year}/{month}")
    suspend fun getMarketStatistic(@Path("year") year: Int,@Path("month") month: Int) : Response<MarketStatisticResponse>

    @GET("statistic/market/")
    suspend fun getAllMarketStatistics() : Response<List<MarketStatisticResponse>>

    @GET("statistic/shareholder/{id}/{year}/{month")
    suspend fun getShareholderStatistic(@Path("id") shareholderId: String,@Path("year") year: Int,@Path("month") month: Int) : Response<MonthStatisticResponse>

    @GET("statistic/shareholder/{id}")
    suspend fun getAllShareholderStatistics(@Path("id") shareholderId: String) : Response<List<MonthStatisticResponse>>

    @PUT("statistic/shareholder/{id}")
    suspend fun updatePaidProperty(@Path("id") shareholderId: String,@Body request: MonthStatisticRequest) : Response<MonthStatisticResponse>
}