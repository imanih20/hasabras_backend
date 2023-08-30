package com.mohyeddin.store_accountent.data.user

import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.market.marketModule
import com.mohyeddin.store_accountent.data.user.remote.UserService
import com.mohyeddin.store_accountent.data.user.repository.UserRepositoryImpl
import com.mohyeddin.store_accountent.domain.market.usecase.GetMarketUseCase
import com.mohyeddin.store_accountent.domain.market.usecase.UpdateMarketUseCase
import com.mohyeddin.store_accountent.domain.user.UserRepository
import com.mohyeddin.store_accountent.presentation.user.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule = module {
    includes(networkModule, marketModule)

    single<UserService>{
        val retrofit: Retrofit = get()
        retrofit.create(UserService::class.java)
    }

    single<UserRepository>{
        UserRepositoryImpl(get(),androidContext())
    }

    viewModel {
        UserViewModel(
            get(),
            GetMarketUseCase(get()),
            updateMarket = UpdateMarketUseCase(get())
        )
    }
}