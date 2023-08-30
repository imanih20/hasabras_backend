package com.mohyeddin.store_accountent.data.product

import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.product.remote.ProductService
import com.mohyeddin.store_accountent.data.product.repository.ProductRepositoryImpl
import com.mohyeddin.store_accountent.data.trade.dbModule
import com.mohyeddin.store_accountent.data.trade.tradeModule
import com.mohyeddin.store_accountent.domain.product.ProductRepository
import com.mohyeddin.store_accountent.domain.product.usecase.GetProductListUseCase
import com.mohyeddin.store_accountent.presentation.product.viewmodels.ProductViewModel
import ir.huri.jcal.JalaliCalendar
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val productModule = module {
    includes(networkModule, dbModule)

    single<ProductService>{
        val retrofit : Retrofit = get()
        retrofit.create(ProductService::class.java)
    }

    single<ProductRepository>{
        ProductRepositoryImpl(get())
    }

    viewModel {
        ProductViewModel(GetProductListUseCase(get()), get(), JalaliCalendar())
    }
}