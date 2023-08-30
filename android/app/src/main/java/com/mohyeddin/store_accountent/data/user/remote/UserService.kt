package com.mohyeddin.store_accountent.data.user.remote

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.user.remote.dto.UserRequest
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserService{
    @GET("user/me")
    suspend fun getMe() : Response<UserResponse>

    @PUT("user/")
    suspend fun updateName(userRequest: UserRequest) : Response<UserResponse>
}