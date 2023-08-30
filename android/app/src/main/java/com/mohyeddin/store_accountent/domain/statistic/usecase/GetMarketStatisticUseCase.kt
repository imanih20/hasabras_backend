package com.mohyeddin.store_accountent.domain.statistic.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MarketStatisticResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.StatisticRepository
import com.mohyeddin.store_accountent.domain.statistic.models.MarketStatistic
import kotlinx.coroutines.flow.Flow

class GetMarketStatisticUseCase(private val repo: StatisticRepository) {
    suspend operator fun invoke(year: Int,month: Int) : Flow<BaseResult<MarketStatistic,WrappedResponse<MarketStatisticResponse>>>{
        return repo.getMarketStatistic(year,month)
    }
}