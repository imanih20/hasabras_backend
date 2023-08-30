package com.mohyeddin.store_accountent.domain.auth

import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.auth.remote.dto.LoginRequest
import com.mohyeddin.store_accountent.data.auth.remote.dto.SignRequest
import com.mohyeddin.store_accountent.data.auth.remote.dto.VerifyRequest
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUser(name: String, phone: String) : Flow<BaseResult<Auth,WrappedResponse<AuthResponse>>>

    suspend fun loginUser(phone: String) : Flow<BaseResult<Auth,WrappedResponse<AuthResponse>>>

//    suspend fun loginShareholder(loginRequest: LoginRequest) : Flow<BaseResult<Auth,WrappedResponse<AuthResponse>>>

    suspend fun verifyUser(code: String) : Flow<BaseResult<User,WrappedResponse<UserResponse>>>

//    suspend fun verifyShareholder(verifyRequest: VerifyRequest) : Flow<BaseResult<Shareholder,WrappedResponse<ShareholderResponse>>>
}