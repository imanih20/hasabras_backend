package com.mohyeddin.store_accountent.data.statistic.repository

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.utils.ShareholderResult
import com.mohyeddin.store_accountent.data.statistic.remote.StatisticService
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MarketStatisticResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticRequest
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticResponse
import com.mohyeddin.store_accountent.data.statistic.utils.MarketStatisticResult
import com.mohyeddin.store_accountent.data.statistic.utils.ShareholderStatisticResult
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.StatisticRepository
import com.mohyeddin.store_accountent.domain.statistic.models.MarketStatistic
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic
import kotlinx.coroutines.flow.Flow

class StatisticRepositoryImpl(private val service: StatisticService) : StatisticRepository {
    private val marketStateResult = MarketStatisticResult()
    private val shareholderStateResult = ShareholderStatisticResult()
    override suspend fun getMarketStatistic(
        year: Int,
        month: Int
    ): Flow<BaseResult<MarketStatistic, WrappedResponse<MarketStatisticResponse>>> {
        val response = service.getMarketStatistic(year,month)
        return marketStateResult.getResult(response)
    }

    override suspend fun getAllMarketStatistics(): Flow<BaseResult<List<MarketStatistic>, WrappedListResponse<MarketStatisticResponse>>> {
        val response = service.getAllMarketStatistics()
        return marketStateResult.getListResult(response)
    }

    override suspend fun getShareholderStatistic(
        shareholderId: String,
        year: Int,
        month: Int
    ): Flow<BaseResult<MonthStatistic, WrappedResponse<MonthStatisticResponse>>> {
        val response = service.getShareholderStatistic(shareholderId,year,month)
        return shareholderStateResult.getResult(response)
    }

    override suspend fun getAllShareholderStatistics(shareholderId: String): Flow<BaseResult<List<MonthStatistic>, WrappedListResponse<MonthStatisticResponse>>> {
        val response = service.getAllShareholderStatistics(shareholderId)
        return shareholderStateResult.getListResult(response)
    }

    override suspend fun updatePaidProperty(
        shareholderId: String,
        isPaid: Boolean
    ): Flow<BaseResult<MonthStatistic, WrappedResponse<MonthStatisticResponse>>> {
        val response = service.updatePaidProperty(shareholderId,MonthStatisticRequest(isPaid))
        return shareholderStateResult.getResult(response)
    }
}