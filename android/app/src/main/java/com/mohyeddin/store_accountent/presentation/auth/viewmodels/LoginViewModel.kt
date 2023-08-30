package com.mohyeddin.store_accountent.presentation.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.data.auth.remote.dto.AuthResponse
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.auth.usecases.LoginUseCase
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.user.model.User
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val loginUser: LoginUseCase) : BaseViewModel(){
    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Init)
    val state get() = _state.asStateFlow()

    fun login(phone: String){
        _state.value = LoginScreenState.Init
        setLoading()
        checkRequest(
            request = {
                loginUser(phone)
            }
        ) {
            hideLoading()
            when(it){
                is BaseResult.Success -> _state.value = LoginScreenState.Success(it.data as Auth)
                is BaseResult.Error -> _state.value = LoginScreenState.Error(it.rawResponse.message,it.rawResponse.code)
            }
        }
    }


}

sealed class LoginScreenState{
    object Init : LoginScreenState()
    data class Success(val data: Auth) : LoginScreenState()
    data class Error(val message: String,val code: Int = 0) : LoginScreenState()
}
