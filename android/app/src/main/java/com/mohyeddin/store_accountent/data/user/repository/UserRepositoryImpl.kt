package com.mohyeddin.store_accountent.data.user.repository

import android.content.Context
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.user.remote.UserService
import com.mohyeddin.store_accountent.data.user.remote.dto.UserRequest
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import com.mohyeddin.store_accountent.data.user.utils.UserResult
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.user.UserRepository
import com.mohyeddin.store_accountent.domain.user.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(private val service: UserService,private val context: Context) : UserRepository{
    override suspend fun getMe(): Flow<BaseResult<User, WrappedResponse<UserResponse>>> {
        val response = service.getMe()
        return UserResult().getResult(response)
    }

    override suspend fun update(name: String) : Flow<BaseResult<User,WrappedResponse<UserResponse>>>{
        var response : Response<UserResponse>? = null
        if (checkInternet(context)){
            response = service.updateName(UserRequest(name))
        }
        return UserResult().getResult(response)
    }
}