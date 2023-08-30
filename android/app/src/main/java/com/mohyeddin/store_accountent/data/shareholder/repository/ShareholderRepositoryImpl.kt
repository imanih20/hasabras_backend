package com.mohyeddin.store_accountent.data.shareholder.repository

import android.content.Context
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.ShareholderService
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderRequest
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.data.shareholder.utils.ShareholderResult
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.ShareholderRepository
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ShareholderRepositoryImpl(private val service: ShareholderService,private val context: Context) : ShareholderRepository {
    private val shareholderResult = ShareholderResult()
    override suspend fun insertShareholder(name: String, phone: String, share: Double): Flow<BaseResult<Shareholder, WrappedResponse<ShareholderResponse>>> {
        var response : Response<ShareholderResponse>? = null
        if (checkInternet(context)){
            response = service.insertShareholder(shareholderRequest = ShareholderRequest(name,phone,share))
        }
        return shareholderResult.getResult(response)
    }

    override suspend fun updateShareholder(
        id: String,
        shareholderRequest: ShareholderRequest
    ): Flow<BaseResult<Shareholder, WrappedResponse<ShareholderResponse>>> {
        var response : Response<ShareholderResponse>? = null
        if (checkInternet(context)){
            response = service.updateShareholder(id,shareholderRequest)
        }
        return shareholderResult.getResult(response)
    }

    override suspend fun deleteShareholder(id: String): Flow<BaseResult<Shareholder, WrappedResponse<ShareholderResponse>>> {
        var response : Response<ShareholderResponse>? = null
        if (checkInternet(context)){
            response = service.deleteShareholder(id)
        }
        return shareholderResult.getResult(response)
    }

    override suspend fun getMarketShareholders(): Flow<BaseResult<List<Shareholder>, WrappedListResponse<ShareholderResponse>>> {
        val response = service.getMarketShareholders()
        return shareholderResult.getListResult(response)
    }
}