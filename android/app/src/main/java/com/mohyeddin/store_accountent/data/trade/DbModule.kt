package com.mohyeddin.store_accountent.data.trade

import androidx.room.Room
import com.mohyeddin.store_accountent.data.trade.database.Database
import com.mohyeddin.store_accountent.data.trade.repository.PurchaseRepositoryImpl
import com.mohyeddin.store_accountent.data.trade.repository.SaleRepoImpl
import com.mohyeddin.store_accountent.domain.trade.repository.PurchaseRepository
import com.mohyeddin.store_accountent.domain.trade.repository.SaleRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "sta_db"
        ).build()
    }

    single<SaleRepository> {
        SaleRepoImpl(get<Database>().saleDao())
    }

    single<PurchaseRepository> {
        PurchaseRepositoryImpl(get<Database>().purchaseDao())
    }

}