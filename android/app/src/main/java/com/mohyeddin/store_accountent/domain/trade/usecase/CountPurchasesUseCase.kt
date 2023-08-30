package com.mohyeddin.store_accountent.domain.trade.usecase

import com.mohyeddin.store_accountent.domain.trade.repository.PurchaseRepository
import kotlinx.coroutines.flow.Flow

class CountPurchasesUseCase(private val repo: PurchaseRepository) {
    operator fun invoke() : Flow<Int>{
        return repo.getCount()
    }
}