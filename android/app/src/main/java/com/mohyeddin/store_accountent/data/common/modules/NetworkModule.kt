package com.mohyeddin.store_accountent.data.common.modules

import android.os.Build
import androidx.annotation.RequiresApi
import com.mohyeddin.store_accountent.common.BASE_URL
import com.mohyeddin.store_accountent.data.common.utils.RequestInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit


val networkModule = module {
    includes(prefsModule)

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            cache(Cache(androidContext().cacheDir,6*1024*1024))
            addInterceptor(RequestInterceptor(get(),androidContext()))
            connectTimeout(20,TimeUnit.SECONDS)
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(get())
            baseUrl(BASE_URL)
        }.build()
    }
}