package com.mohyeddin.store_accountent.data.product.repository

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.product.remote.ProductService
import com.mohyeddin.store_accountent.data.product.remote.dto.ProductResponse
import com.mohyeddin.store_accountent.data.product.utils.ProductResult
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.product.ProductRepository
import com.mohyeddin.store_accountent.domain.product.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val service: ProductService) : ProductRepository {
    private val productResult = ProductResult()
//    override suspend fun deleteProduct(id: String): Flow<BaseResult<Product, WrappedResponse<ProductResponse>>> {
//        val response = service.deleteProduct(id)
//        return productResult.getResult(response)
//    }

    override suspend fun getMarketProducts(): Flow<BaseResult<List<Product>, WrappedListResponse<ProductResponse>>>{
        val response = service.getMarketProducts()
        return productResult.getListResult(response)
    }

//    override suspend fun getShareholderProducts(id: String): Flow<BaseResult<List<Product>, WrappedListResponse<ProductResponse>>> {
//        val response = service.getShareholderProducts(id)
//        return productResult.getListResult(response)
//    }
}