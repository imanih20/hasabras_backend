package com.mohyeddin.store_accountent.data.auth.utils

import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.domain.auth.model.Auth

class AuthResult : ResultMaker<Auth,AuthResponse>() {
    override fun getModel(res: AuthResponse): Auth {
        return Auth(res.token)
    }
}