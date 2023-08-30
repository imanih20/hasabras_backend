package com.mohyeddin.store_accountent.domain.trade.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.trade.remote.dto.TradeResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import com.mohyeddin.store_accountent.domain.trade.repository.TradeRepository
import kotlinx.coroutines.flow.Flow

class AddPurchasesUseCase(private val repo: TradeRepository) {
    suspend operator fun invoke(purchaseList: List<Purchase>): Flow<BaseResult<List<Trade>,WrappedListResponse<TradeResponse>>>{
        return repo.addPurchases(purchaseList)
    }
}