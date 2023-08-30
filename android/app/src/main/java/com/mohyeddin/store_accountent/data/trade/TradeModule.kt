package com.mohyeddin.store_accountent.data.trade

import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.common.modules.prefsModule
import com.mohyeddin.store_accountent.data.product.productModule
import com.mohyeddin.store_accountent.data.shareholder.shareholderModule
import com.mohyeddin.store_accountent.data.statistic.statisticModule
import com.mohyeddin.store_accountent.data.trade.remote.TradeService
import com.mohyeddin.store_accountent.data.trade.repository.TradeRepositoryImpl
import com.mohyeddin.store_accountent.domain.product.usecase.GetProductListUseCase
import com.mohyeddin.store_accountent.domain.shareholder.usecase.GetAllShareholdersUseCase
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetMarketStatisticsUseCase
import com.mohyeddin.store_accountent.domain.trade.repository.TradeRepository
import com.mohyeddin.store_accountent.domain.trade.usecase.AddPurchasesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.AddSalesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.CountPurchasesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.CountSalesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.GetDateTradesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.GetTradesUseCase
import com.mohyeddin.store_accountent.presentation.dashboard.viewmodel.DashboardViewModel
import com.mohyeddin.store_accountent.presentation.main.viewmodel.MainViewModel
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.AddPurchaseViewModel
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.PurchaseListViewModel
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.SaleListViewModel
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.TradesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val tradeModule = module {
    includes(networkModule, shareholderModule, productModule, statisticModule, dbModule, prefsModule)

    single<TradeService> {
        val retrofit : Retrofit = get()
        retrofit.create(TradeService::class.java)
    }

    single<TradeRepository> {
        TradeRepositoryImpl(get(),androidContext())
    }

    viewModel {
        TradesViewModel(GetTradesUseCase(get()),get())
    }

    viewModel {
        AddPurchaseViewModel(
            GetAllShareholdersUseCase(get()),
            GetProductListUseCase(get()),
            get()
        )
    }

    viewModel {
        PurchaseListViewModel(
            get(),
            AddPurchasesUseCase(get())
        )
    }

    viewModel {
        SaleListViewModel(
            get(),
            GetProductListUseCase(get()),
            AddSalesUseCase(get())
        )
    }

    viewModel {
        DashboardViewModel(
            getTrades = GetDateTradesUseCase(get()),
            getStatistics = GetMarketStatisticsUseCase(get())
        )
    }

    viewModel {
        MainViewModel(
            CountPurchasesUseCase(get()),
            CountSalesUseCase(get())
        )
    }
}