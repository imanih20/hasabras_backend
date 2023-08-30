package com.mohyeddin.store_accountent.data.auth.remote

import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.auth.remote.dto.LoginRequest
import com.mohyeddin.store_accountent.data.auth.remote.dto.SignRequest
import com.mohyeddin.store_accountent.data.auth.remote.dto.VerifyRequest
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signOwner")
    suspend fun signUser(@Body signRequest: SignRequest) : Response<AuthResponse>

    @POST("auth/loginOwner")
    suspend fun loginUser(@Body loginRequest: LoginRequest) : Response<AuthResponse>

//    @POST("auth/loginShareholder")
//    suspend fun loginShareholder(@Body loginRequest: LoginRequest) : Response<AuthResponse>

    @POST("auth/verifyOwner")
    suspend fun verifyUser(@Body verifyRequest: VerifyRequest) : Response<UserResponse>

//    @POST("auth/verifyShareholder")
//    suspend fun verifyShareholder(@Body verifyRequest: VerifyRequest) : Response<ShareholderResponse>
}