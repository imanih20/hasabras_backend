package com.mohyeddin.store_accountent.data.common.modules

import com.mohyeddin.store_accountent.common.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val prefsModule = module {
    factory { Prefs(androidContext()) }
}