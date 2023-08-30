package com.mohyeddin.store_accountent

import android.app.Application
import com.mohyeddin.store_accountent.data.auth.authModule
import com.mohyeddin.store_accountent.data.common.modules.networkModule
import com.mohyeddin.store_accountent.data.common.modules.prefsModule
import com.mohyeddin.store_accountent.data.market.marketModule
import com.mohyeddin.store_accountent.data.product.productModule
import com.mohyeddin.store_accountent.data.shareholder.shareholderModule
import com.mohyeddin.store_accountent.data.statistic.statisticModule
import com.mohyeddin.store_accountent.data.trade.tradeModule
import com.mohyeddin.store_accountent.data.user.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(authModule, marketModule, productModule, shareholderModule, statisticModule,
                tradeModule, userModule, networkModule, prefsModule)
        }
    }
}