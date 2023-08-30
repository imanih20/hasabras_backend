package com.mohyeddin.store_accountent.presentation.trade.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.common.Prefs
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import com.mohyeddin.store_accountent.domain.trade.usecase.GetTradesUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TradesViewModel(
    private val getTrades: GetTradesUseCase,
    private val prefs: Prefs
) : BaseViewModel() {

    private val _tradeList = MutableStateFlow<List<Trade>>(emptyList())
    val tradeList : StateFlow<List<Trade>> get() = _tradeList

    init{
        fetchTrades()
    }


    private fun fetchTrades() {
        viewModelScope.launch {
            setLoading()
            getTrades()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{ result ->
                    hideLoading()
                    when(result){
                        is BaseResult.Success -> {
                            val date = prefs.readFilterTradeDate()
                            val type = prefs.readFilterTradeType()
                            _tradeList.value = result.data.filter {
                                if (date.isNullOrEmpty()) true
                                else it.date.withEnglishNumber() == date
                            }.filter {
                                if (type.isNullOrEmpty()) true
                                else it.type==type
                            }
                        }
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }
}