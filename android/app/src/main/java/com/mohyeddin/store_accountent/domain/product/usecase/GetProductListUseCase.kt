package com.mohyeddin.store_accountent.domain.product.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.product.remote.dto.ProductResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.product.ProductRepository
import com.mohyeddin.store_accountent.domain.product.model.Product
import kotlinx.coroutines.flow.Flow

class GetProductListUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke() : Flow<BaseResult<List<Product>,WrappedListResponse<ProductResponse>>>{
        return repo.getMarketProducts()
    }
}