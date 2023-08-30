package com.mohyeddin.store_accountent.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.trade.usecase.CountPurchasesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.CountSalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch

class MainViewModel(
    private val countPurchases: CountPurchasesUseCase,
    private val countSales: CountSalesUseCase
) : ViewModel(){

    init {
        count()
    }

    private val _purchaseCount = MutableStateFlow(0)
    val purchaseCount : StateFlow<Int> get() = _purchaseCount

    private val _saleCount = MutableStateFlow(0)
    val saleCount : StateFlow<Int> get() = _saleCount

    private fun count(){
        viewModelScope.launch{
            countPurchases()
                .collect{
                    _purchaseCount.value = it
                }
        }
        viewModelScope.launch {
            countSales()
                .collect{
                    _saleCount.value = it
                }
        }
    }
}