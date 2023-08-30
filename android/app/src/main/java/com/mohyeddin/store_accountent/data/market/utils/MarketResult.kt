package com.mohyeddin.store_accountent.data.market.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.market.remote.dto.MarketResponse
import com.mohyeddin.store_accountent.domain.market.model.Market

class MarketResult : ResultMaker<Market,MarketResponse>() {
    override fun getModel(res: MarketResponse): Market {
        return Market(res.id,res.name,res.ownerId)
    }
}