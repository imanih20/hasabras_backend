package com.mohyeddin.store_accountent.domain.statistic.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.StatisticRepository
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic
import kotlinx.coroutines.flow.Flow

class UpdateIsPaidUseCase(
    private val repo: StatisticRepository
) {
    suspend operator fun invoke(statisticId: String, isPaid: Boolean) : Flow<BaseResult<MonthStatistic,WrappedResponse<MonthStatisticResponse>>>{
        return repo.updatePaidProperty(statisticId,isPaid)
    }
}