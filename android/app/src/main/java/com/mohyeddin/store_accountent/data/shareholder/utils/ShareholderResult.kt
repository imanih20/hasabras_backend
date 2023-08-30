package com.mohyeddin.store_accountent.data.shareholder.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder

class ShareholderResult : ResultMaker<Shareholder,ShareholderResponse>() {
    override fun getModel(res: ShareholderResponse): Shareholder {
        return Shareholder(res.id,res.name,res.phone,res.share)
    }
}