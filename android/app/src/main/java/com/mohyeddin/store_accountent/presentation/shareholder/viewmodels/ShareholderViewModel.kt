package com.mohyeddin.store_accountent.presentation.shareholder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.domain.shareholder.usecase.GetAllShareholdersUseCase
import com.mohyeddin.store_accountent.domain.shareholder.usecase.InsertShareholderUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ShareholderViewModel(
    private val  insertShareholder:  InsertShareholderUseCase,
    private val getAllShareholders: GetAllShareholdersUseCase
) : BaseViewModel() {


    private val _shareholders  = MutableStateFlow<List<Shareholder>>(emptyList())
    val shareholders : StateFlow<List<Shareholder>> get() = _shareholders

    init {
        fetchAllShareholders()
    }



    private fun fetchAllShareholders(){
        setLoading()
        viewModelScope.launch {
            getAllShareholders()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> {
                            _shareholders.value = it.data
                        }
                        is  BaseResult.Error -> {
                            showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                        }
                    }
                }

        }
    }

    fun addShareholder(name: String, phone: String, share: Double){
        setLoading()
        checkRequest(
            request = {
                insertShareholder(name,phone, share)
            }
        ){
            hideLoading()
            when(it){
                is BaseResult.Success -> {
                    fetchAllShareholders()
                }
                is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
            }
        }
    }
}


