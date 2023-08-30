package com.mohyeddin.store_accountent.domain.market

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketRequest
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.market.model.Market
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    suspend fun insertMarket(name: String) : Flow<BaseResult<Market,WrappedResponse<MarketResponse>>>

    suspend fun updateMarket(name: String) : Flow<BaseResult<Market,WrappedResponse<MarketResponse>>>

//    suspend fun deleteMarket(id: String) : Flow<BaseResult<Market,WrappedResponse<MarketResponse>>>

    suspend fun getMarket() : Flow<BaseResult<Market,WrappedResponse<MarketResponse>>>
}