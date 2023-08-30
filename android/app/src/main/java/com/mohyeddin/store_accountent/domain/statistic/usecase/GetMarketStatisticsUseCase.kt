package com.mohyeddin.store_accountent.domain.statistic.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MarketStatisticResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.StatisticRepository
import com.mohyeddin.store_accountent.domain.statistic.models.MarketStatistic
import kotlinx.coroutines.flow.Flow

class GetMarketStatisticsUseCase(private val repo: StatisticRepository) {
    suspend operator fun invoke() : Flow<BaseResult<List<MarketStatistic>,WrappedListResponse<MarketStatisticResponse>>>{
        return repo.getAllMarketStatistics()
    }
}