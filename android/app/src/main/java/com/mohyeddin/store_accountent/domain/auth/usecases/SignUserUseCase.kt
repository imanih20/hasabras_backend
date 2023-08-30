package com.mohyeddin.store_accountent.domain.auth.usecases

import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.auth.remote.dto.SignRequest
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.domain.auth.AuthRepository
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

class SignUserUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(name: String,phone: String) : Flow<BaseResult<Auth,WrappedResponse<AuthResponse>>>{
        return repo.signUser(name, phone)
    }
}