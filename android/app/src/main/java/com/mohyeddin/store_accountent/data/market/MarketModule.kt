package com.mohyeddin.store_accountent.data.market

import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.market.remote.MarketService
import com.mohyeddin.store_accountent.data.market.repository.MarketRepositoryImpl
import com.mohyeddin.store_accountent.domain.market.MarketRepository
import com.mohyeddin.store_accountent.domain.market.usecase.InsertMarketUseCase
import com.mohyeddin.store_accountent.presentation.market.viewmodels.CreateMarketViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val marketModule = module {
    includes(networkModule)

    single<MarketService>{
        val retrofit: Retrofit = get()
        retrofit.create(MarketService::class.java)
    }

    single<MarketRepository> {
        MarketRepositoryImpl(get(),androidContext())
    }

    viewModel{
        CreateMarketViewModel(InsertMarketUseCase(get()))
    }
}