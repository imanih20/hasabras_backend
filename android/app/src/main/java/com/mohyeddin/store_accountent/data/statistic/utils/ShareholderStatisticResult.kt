package com.mohyeddin.store_accountent.data.statistic.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticResponse
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic

class ShareholderStatisticResult : ResultMaker<MonthStatistic,MonthStatisticResponse>() {
    override fun getModel(res: MonthStatisticResponse): MonthStatistic {
        return MonthStatistic(res.id,res.shareholderId,res.totalPurchase,res.totalSale,res.totalProfit,res.year,res.month,res.isPaid)
    }
}