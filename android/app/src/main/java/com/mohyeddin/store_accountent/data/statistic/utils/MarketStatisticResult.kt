package com.mohyeddin.store_accountent.data.statistic.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MarketStatisticResponse
import com.mohyeddin.store_accountent.domain.statistic.models.MarketStatistic

class MarketStatisticResult : ResultMaker<MarketStatistic,MarketStatisticResponse>() {
    override fun getModel(res: MarketStatisticResponse): MarketStatistic {
        return MarketStatistic(res.id,res.monthSale,res.monthPurchase,res.monthProfit,res.monthOwnerSale,res.monthOwnerPurchase,res.monthOwnerProfit,res.year,res.month)
    }
}