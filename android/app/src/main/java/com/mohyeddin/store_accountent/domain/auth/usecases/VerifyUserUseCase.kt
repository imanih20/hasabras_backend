package com.mohyeddin.store_accountent.domain.auth.usecases

import com.mohyeddin.store_accountent.data.auth.remote.dto.VerifyRequest
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import com.mohyeddin.store_accountent.domain.auth.AuthRepository
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.user.model.User
import kotlinx.coroutines.flow.Flow

class VerifyUserUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(code: String) : Flow<BaseResult<User,WrappedResponse<UserResponse>>> {
        return repo.verifyUser(code)
    }
}