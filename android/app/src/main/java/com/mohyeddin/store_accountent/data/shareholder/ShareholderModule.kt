package com.mohyeddin.store_accountent.data.shareholder

import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.shareholder.remote.ShareholderService
import com.mohyeddin.store_accountent.data.shareholder.repository.ShareholderRepositoryImpl
import com.mohyeddin.store_accountent.domain.shareholder.ShareholderRepository
import com.mohyeddin.store_accountent.domain.shareholder.usecase.GetAllShareholdersUseCase
import com.mohyeddin.store_accountent.domain.shareholder.usecase.InsertShareholderUseCase
import com.mohyeddin.store_accountent.presentation.shareholder.viewmodels.ShareholderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val shareholderModule = module {
    includes(networkModule)

    single<ShareholderService>{
        val retrofit : Retrofit = get()
        retrofit.create(ShareholderService::class.java)
    }

    single<ShareholderRepository> {
        ShareholderRepositoryImpl(get(),androidContext())
    }

    viewModel {
        ShareholderViewModel(
            InsertShareholderUseCase(get()),
            GetAllShareholdersUseCase(get())
        )
    }
}