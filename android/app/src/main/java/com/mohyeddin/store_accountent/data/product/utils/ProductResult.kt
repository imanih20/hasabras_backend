package com.mohyeddin.store_accountent.data.product.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.product.remote.dto.ProductResponse
import com.mohyeddin.store_accountent.domain.product.model.Product

class ProductResult : ResultMaker<Product,ProductResponse>() {
    override fun getModel(res: ProductResponse): Product {
        return Product(res.id,res.title,res.price,res.quantity,res.profit,res.ownerId,res.unit)
    }
}