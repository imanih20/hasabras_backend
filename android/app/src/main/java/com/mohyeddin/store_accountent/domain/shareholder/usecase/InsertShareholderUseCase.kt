package com.mohyeddin.store_accountent.domain.shareholder.usecase

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.ShareholderRepository
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import kotlinx.coroutines.flow.Flow

class InsertShareholderUseCase(private val repo: ShareholderRepository) {
    suspend operator fun invoke(name: String,phone: String,share: Double): Flow<BaseResult<Shareholder,WrappedResponse<ShareholderResponse>>>{
        return repo.insertShareholder(name,phone, share)
    }
}