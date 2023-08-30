package com.mohyeddin.store_accountent.presentation.auth.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.auth.usecases.SignUserUseCase
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SignViewModel(private val signUser: SignUserUseCase) : BaseViewModel() {
    private val _state = MutableStateFlow<SignScreenState>(SignScreenState.Init)
    val state get() = _state.asStateFlow()

    fun sign(name: String, phone: String) {
        _state.value = SignScreenState.Init
        setLoading()
        checkRequest(
            request = {
                signUser(name, phone)
            }
        ){
            hideLoading()
            when(it){
                is BaseResult.Success->_state.value = SignScreenState.Success(it.data as Auth)
                is BaseResult.Error -> _state.value = SignScreenState.Error(it.rawResponse.message,it.rawResponse.code)
            }
        }
    }
}

sealed class SignScreenState{
    object Init : SignScreenState()
    data class Success(val data: Auth) : SignScreenState()
    data class Error(val message: String, val code: Int = 0) : SignScreenState()
}
