package com.mohyeddin.store_accountent.domain.market.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.market.MarketRepository
import com.mohyeddin.store_accountent.domain.market.model.Market
import kotlinx.coroutines.flow.Flow

class GetMarketUseCase(private val repo: MarketRepository) {
    suspend operator fun  invoke() : Flow<BaseResult<Market,WrappedResponse<MarketResponse>>>{
        return repo.getMarket()
    }
}