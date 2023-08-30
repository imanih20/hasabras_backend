package com.mohyeddin.store_accountent.domain.trade.usecase

import com.mohyeddin.store_accountent.domain.trade.repository.SaleRepository
import kotlinx.coroutines.flow.Flow

class CountSalesUseCase(private val repo: SaleRepository) {
    operator fun invoke() : Flow<Int>{
        return repo.getCount()
    }
}