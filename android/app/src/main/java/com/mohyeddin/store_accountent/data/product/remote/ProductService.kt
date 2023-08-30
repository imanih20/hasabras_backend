package com.mohyeddin.store_accountent.data.product.remote

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.product.remote.dto.ProductResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
//    @DELETE("product/{id}")
//    suspend fun deleteProduct(@Path("id") id : String) : Response<ProductResponse>

    @GET("product/market/")
    suspend fun getMarketProducts() : Response<List<ProductResponse>>

//    @GET("product/shareholder/{id}")
//    suspend fun getShareholderProducts(@Path("id") id: String) : Response<List<ProductResponse>>
}