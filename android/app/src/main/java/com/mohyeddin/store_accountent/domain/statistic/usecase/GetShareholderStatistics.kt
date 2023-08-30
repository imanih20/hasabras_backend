package com.mohyeddin.store_accountent.domain.statistic.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.statistic.remote.dto.MonthStatisticResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.StatisticRepository
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic
import kotlinx.coroutines.flow.Flow

class GetShareholderStatistics(private val repo: StatisticRepository) {
    suspend operator fun invoke(shareholderId: String) : Flow<BaseResult<List<MonthStatistic>,WrappedListResponse<MonthStatisticResponse>>>{
        return repo.getAllShareholderStatistics(shareholderId)
    }
}