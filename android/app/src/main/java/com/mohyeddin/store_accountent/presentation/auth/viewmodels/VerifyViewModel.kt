package com.mohyeddin.store_accountent.presentation.auth.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import com.mohyeddin.store_accountent.domain.auth.model.Auth
import com.mohyeddin.store_accountent.domain.auth.usecases.VerifyUserUseCase
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class VerifyViewModel(private val verifyUser: VerifyUserUseCase) : BaseViewModel(){
    private val _state = MutableStateFlow<VerifyScreenState>(VerifyScreenState.Init)
    val state get() = _state.asStateFlow()

    fun verify(code: String){
        _state.value = VerifyScreenState.Init
        setLoading()
        checkRequest(request = {
            verifyUser(code)
        }) {
            hideLoading()
           when(it){
               is BaseResult.Success -> _state.value = VerifyScreenState.Success
               is BaseResult.Error -> _state.value = VerifyScreenState.Error(it.rawResponse.message,it.rawResponse.code)
           }
        }
    }
}
sealed class VerifyScreenState{
    object Init : VerifyScreenState()
    object Success: VerifyScreenState()
    data class Error(val message: String, val code: Int = 0) : VerifyScreenState()
}
