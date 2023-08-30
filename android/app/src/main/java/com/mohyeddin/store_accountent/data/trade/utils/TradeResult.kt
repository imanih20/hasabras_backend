package com.mohyeddin.store_accountent.data.trade.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.trade.remote.dto.TradeResponse
import com.mohyeddin.store_accountent.domain.trade.model.Trade

class TradeResult : ResultMaker<Trade,TradeResponse>() {
    override fun getModel(res: TradeResponse): Trade {
        return Trade(res.id,res.productName,res.quantity,res.totalPrice,res.type,res.unit,res.date)
    }
}