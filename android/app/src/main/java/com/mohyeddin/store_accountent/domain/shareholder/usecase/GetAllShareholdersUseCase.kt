package com.mohyeddin.store_accountent.domain.shareholder.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.ShareholderRepository
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import kotlinx.coroutines.flow.Flow

class GetAllShareholdersUseCase(private val repo: ShareholderRepository) {
    suspend operator fun invoke() : Flow<BaseResult<List<Shareholder>,WrappedListResponse<ShareholderResponse>>>{
        return repo.getMarketShareholders()
    }
}