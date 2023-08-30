package com.mohyeddin.store_accountent.data.auth.repository

import android.content.Context
import android.util.Log
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.data.auth.remote.AuthService
import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.auth.remote.dto.LoginRequest
import com.mohyeddin.store_accountent.data.auth.remote.dto.SignRequest
import com.mohyeddin.store_accountent.data.auth.remote.dto.VerifyRequest
import com.mohyeddin.store_accountent.data.auth.utils.AuthResult
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.shareholder.remote.dto.ShareholderResponse
import com.mohyeddin.store_accountent.data.shareholder.utils.ShareholderResult
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import com.mohyeddin.store_accountent.data.user.utils.UserResult
import com.mohyeddin.store_accountent.domain.auth.AuthRepository
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.domain.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class AuthRepositoryImpl(private val service: AuthService,private val context: Context) : AuthRepository {

    override suspend fun signUser(name: String, phone: String): Flow<BaseResult<Auth, WrappedResponse<AuthResponse>>> {
        var response : Response<AuthResponse>? = null
        if (checkInternet(context)){
            response = service.signUser(SignRequest(name,phone.withEnglishNumber()))
        }
        return AuthResult().getResult(response)
    }

    override suspend fun loginUser(phone: String): Flow<BaseResult<Auth, WrappedResponse<AuthResponse>>> {
        var response : Response<AuthResponse>? = null
        if (checkInternet(context)){
            response = service.loginUser(LoginRequest(phone.withEnglishNumber()))
        }
        return AuthResult().getResult(response)
    }

//    override suspend fun loginShareholder(loginRequest: LoginRequest): Flow<BaseResult<Auth, WrappedResponse<AuthResponse>>> {
//        val response = service.loginShareholder(loginRequest)
//        return AuthResult().getResult(response)
//    }

    override suspend fun verifyUser(code: String): Flow<BaseResult<User, WrappedResponse<UserResponse>>> {
        var response : Response<UserResponse>? = null
        if (checkInternet(context)){
            response = service.verifyUser(VerifyRequest(code.withEnglishNumber()))
        }
        return UserResult().getResult(response)
    }
//
//    override suspend fun verifyShareholder(verifyRequest: VerifyRequest): Flow<BaseResult<Shareholder, WrappedResponse<ShareholderResponse>>> {
//        val response = service.verifyShareholder(verifyRequest)
//        return ShareholderResult().getResult(response)
//    }
}