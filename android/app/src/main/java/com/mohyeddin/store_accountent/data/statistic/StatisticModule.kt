package com.mohyeddin.store_accountent.data.statistic

import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.statistic.remote.StatisticService
import com.mohyeddin.store_accountent.data.statistic.repository.StatisticRepositoryImpl
import com.mohyeddin.store_accountent.domain.shareholder.usecase.GetAllShareholdersUseCase
import com.mohyeddin.store_accountent.domain.statistic.StatisticRepository
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetMarketStatisticsUseCase
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetShareholderStatistics
import com.mohyeddin.store_accountent.domain.statistic.usecase.UpdateIsPaidUseCase
import com.mohyeddin.store_accountent.presentation.statistic.viewmodels.ShareholderStatisticViewModel
import com.mohyeddin.store_accountent.presentation.statistic.viewmodels.StatisticViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val statisticModule = module {
    includes(networkModule)

    single<StatisticService>{
        val retrofit : Retrofit = get()
        retrofit.create(StatisticService::class.java)
    }

    single<StatisticRepository> {
        StatisticRepositoryImpl(get())
    }

    viewModel {
        StatisticViewModel(
            GetAllShareholdersUseCase(get()),
            GetShareholderStatistics(get()),
            GetMarketStatisticsUseCase(get())
        )
    }

    viewModel {
        ShareholderStatisticViewModel(
            GetShareholderStatistics(get()),
            UpdateIsPaidUseCase(get()),
            it.get()
        )
    }
}