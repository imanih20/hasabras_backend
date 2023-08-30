package com.mohyeddin.store_accountent.data.shareholder.remote

import com.google.gson.annotations.SerializedName
import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderRequest
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShareholderService {
    @POST("shareholder/")
    suspend fun insertShareholder(@Body shareholderRequest: ShareholderRequest) : Response<ShareholderResponse>

    @DELETE("shareholder/{id}")
    suspend fun deleteShareholder(@Path("id") id: String) : Response<ShareholderResponse>

    @PUT("shareholder/{id}")
    suspend fun updateShareholder(@Path("id") id: String,@Body shareholderRequest: ShareholderRequest) : Response<ShareholderResponse>

    @GET("shareholder/market/")
    suspend fun getMarketShareholders() : Response<List<ShareholderResponse>>
}