package com.mohyeddin.store_accountent.data.common.utils

import android.content.Context
import com.mohyeddin.store_accountent.common.Prefs
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RequestInterceptor(private val prefs: Prefs, private val context: Context) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = prefs.getToken()
        request = if (checkInternet(context))
            request
                .newBuilder()
                .header("Cache-Control", "public, max-age=" + 5)
                .addHeader("x-auth-token",token?:"")
                .build()
        else
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        return chain.proceed(request)
    }
}