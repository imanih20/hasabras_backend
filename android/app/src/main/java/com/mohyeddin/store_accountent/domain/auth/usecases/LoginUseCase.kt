package com.mohyeddin.store_accountent.domain.auth.usecases

import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.auth.remote.dto.LoginRequest
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.domain.auth.AuthRepository
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

class LoginUseCase(val repository: AuthRepository) {
    suspend operator fun invoke(phone: String): Flow<BaseResult<Auth, WrappedResponse<AuthResponse>>>{
        return repository.loginUser(phone)
    }
}