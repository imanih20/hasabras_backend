package com.mohyeddin.store_accountent.domain.user

import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getMe() : Flow<BaseResult<User,WrappedResponse<UserResponse>>>

    suspend fun update(name: String) : Flow<BaseResult<User,WrappedResponse<UserResponse>>>
}