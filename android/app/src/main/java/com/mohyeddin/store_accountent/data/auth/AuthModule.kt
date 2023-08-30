package com.mohyeddin.store_accountent.data.auth

import com.mohyeddin.store_accountent.data.auth.remote.AuthService
import com.mohyeddin.store_accountent.data.auth.repository.AuthRepositoryImpl
import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.domain.auth.AuthRepository
import com.mohyeddin.store_accountent.domain.auth.usecases.LoginUseCase
import com.mohyeddin.store_accountent.domain.auth.usecases.SignUserUseCase
import com.mohyeddin.store_accountent.domain.auth.usecases.VerifyUserUseCase
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.LoginViewModel
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.SignViewModel
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.VerifyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    includes(networkModule)

    single<AuthService> {
        val retrofit : Retrofit = get()
        retrofit.create(AuthService::class.java)
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get(),androidContext())
    }


    viewModel {
        LoginViewModel(loginUser = LoginUseCase(get()))
    }

    viewModel {
        SignViewModel(SignUserUseCase(get()))
    }

    viewModel {
        VerifyViewModel(VerifyUserUseCase(get()))
    }
}