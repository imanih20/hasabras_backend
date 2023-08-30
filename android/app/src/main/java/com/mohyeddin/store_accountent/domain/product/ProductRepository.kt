package com.mohyeddin.store_accountent.domain.product

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.product.remote.dto.ProductResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.product.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
//    suspend fun deleteProduct(id: String) : Flow<BaseResult<Product,WrappedResponse<ProductResponse>>>

    suspend fun getMarketProducts() : Flow<BaseResult<List<Product>,WrappedListResponse<ProductResponse>>>

//    suspend fun getShareholderProducts(id: String) : Flow<BaseResult<List<Product>,WrappedListResponse<ProductResponse>>>
}