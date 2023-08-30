package com.mohyeddin.store_accountent.domain.shareholder

import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderRequest
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import kotlinx.coroutines.flow.Flow

interface ShareholderRepository {
    suspend fun insertShareholder(name: String,phone: String,share: Double) : Flow<BaseResult<Shareholder,WrappedResponse<ShareholderResponse>>>

    suspend fun updateShareholder(id: String,shareholderRequest: ShareholderRequest) : Flow<BaseResult<Shareholder,WrappedResponse<ShareholderResponse>>>

    suspend fun deleteShareholder(id: String) : Flow<BaseResult<Shareholder,WrappedResponse<ShareholderResponse>>>

    suspend fun getMarketShareholders() : Flow<BaseResult<List<Shareholder>,WrappedListResponse<ShareholderResponse>>>
}