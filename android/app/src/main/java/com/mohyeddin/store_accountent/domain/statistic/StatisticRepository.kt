package com.mohyeddin.store_accountent.domain.statistic

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MarketStatisticResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticRequest
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.models.MarketStatistic
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StatisticRepository {
    suspend fun getMarketStatistic(year: Int, month: Int) : Flow<BaseResult<MarketStatistic,WrappedResponse<MarketStatisticResponse>>>

    suspend fun getAllMarketStatistics() : Flow<BaseResult<List<MarketStatistic>,WrappedListResponse<MarketStatisticResponse>>>

    suspend fun getShareholderStatistic( shareholderId: String, year: Int, month: Int) : Flow<BaseResult<MonthStatistic,WrappedResponse<MonthStatisticResponse>>>

    suspend fun getAllShareholderStatistics(shareholderId: String) : Flow<BaseResult<List<MonthStatistic>,WrappedListResponse<MonthStatisticResponse>>>

    suspend fun updatePaidProperty(shareholderId: String, isPaid: Boolean) : Flow<BaseResult<MonthStatistic,WrappedResponse<MonthStatisticResponse>>>

}