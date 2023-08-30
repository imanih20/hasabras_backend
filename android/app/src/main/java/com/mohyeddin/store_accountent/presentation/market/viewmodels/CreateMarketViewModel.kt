package com.mohyeddin.store_accountent.presentation.market.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.market.usecase.InsertMarketUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CreateMarketViewModel(private val insertMarket: InsertMarketUseCase) : BaseViewModel(){

    fun insert(name: String,onSuccess: ()->Unit) {
        setLoading()
        checkRequest(request = {
            insertMarket(name)
        }) {
            hideLoading()
            when(it){
                is BaseResult.Success -> onSuccess()
                is BaseResult.Error -> {
                    showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                }
            }
        }
    }
}

sealed class CreateMarketState{
    object Init : CreateMarketState()
    object Success: CreateMarketState()
    data class Error(val message: String, val code: Int = 0) : CreateMarketState()
}