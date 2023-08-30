package com.mohyeddin.store_accountent.data.market.repository

import android.content.Context
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.market.remote.MarketService
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketRequest
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketResponse
import com.mohyeddin.store_accountent.data.market.utils.MarketResult
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.market.MarketRepository
import com.mohyeddin.store_accountent.domain.market.model.Market
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MarketRepositoryImpl(private val service: MarketService,private val context: Context) : MarketRepository {
    private val marketResult = MarketResult()
    override suspend fun insertMarket(name: String): Flow<BaseResult<Market, WrappedResponse<MarketResponse>>> {
        var response: Response<MarketResponse>? = null
        if(checkInternet(context)){
            response = service.insertMarket(MarketRequest(name))
        }
        return marketResult.getResult(response)
    }

    override suspend fun updateMarket(
        name: String
    ): Flow<BaseResult<Market, WrappedResponse<MarketResponse>>> {
        var response: Response<MarketResponse>? = null
        if(checkInternet(context)){
            response = service.updateMarket(MarketRequest(name))
        }
        return marketResult.getResult(response)
    }

//    override suspend fun deleteMarket(id: String): Flow<BaseResult<Market, WrappedResponse<MarketResponse>>> {
//        var response: Response<MarketResponse>? = null
//        if (checkInternet(context)){
//            response = service.deleteMarket(id)
//        }
//        return marketResult.getResult(response)
//    }

    override suspend fun getMarket(): Flow<BaseResult<Market, WrappedResponse<MarketResponse>>> {
        val response = service.getMarket()
        return marketResult.getResult(response)
    }
}