package com.mohyeddin.store_accountent.presentation.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetMarketStatisticsUseCase
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import com.mohyeddin.store_accountent.domain.trade.usecase.GetDateTradesUseCase
import com.mohyeddin.store_accountent.domain.trade.usecase.GetTradesUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getTrades: GetDateTradesUseCase,
    private val getStatistics: GetMarketStatisticsUseCase,
    private val calendar: JalaliCalendar = JalaliCalendar()
) : BaseViewModel(){


    private val _purchase = MutableStateFlow(0)
    private val _sale = MutableStateFlow(0)
    private val _profit = MutableStateFlow(0)
    private val _lastPurchases = MutableStateFlow<Array<Int>>(emptyArray())
    private val _lastSales = MutableStateFlow<Array<Int>>(emptyArray())
    private val _lastProfits = MutableStateFlow<Array<Int>>(emptyArray())
    private val _trades = MutableStateFlow<List<Trade>>(emptyList())
    private val _isTradeLoading = MutableStateFlow(false)
    init {
        getStatisticList()
        getTradeList()
    }

    val isTradeLoading get() = _isTradeLoading.asStateFlow()
    val purchase : StateFlow<Int> get() = _purchase
    val sale : StateFlow<Int> get() = _sale
    val profit : StateFlow<Int> get() = _profit
    val lastPurchases : StateFlow<Array<Int>> get() = _lastPurchases
    val lastSales : StateFlow<Array<Int>> get() = _lastSales
    val lastProfits : StateFlow<Array<Int>> get() = _lastProfits
    val trades : StateFlow<List<Trade>> get() = _trades


    private fun setTradeLoading(){
        _isTradeLoading.value = true
    }

    private fun hideTradeLoading(){
        _isTradeLoading.value = false
    }
    private fun getTradeList(){
        viewModelScope.launch {
            setTradeLoading()
            getTrades(calendar.toString())
                .catch {
                    hideTradeLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideTradeLoading()
                    when(it){
                        is BaseResult.Success -> _trades.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }

        }
    }

    private fun getStatisticList(){
        viewModelScope.launch {
            setLoading()
            getStatistics()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{ baseResult ->
                    hideLoading()
                    when(baseResult){
                        is BaseResult.Success -> {
                            val date = baseResult.data.sortedBy { it.month }.sortedBy { it.year }
                            launch {
                                _lastProfits.value = date.map { it.marketProfit }.toTypedArray()
                                _lastSales.value = date.map { it.marketSale }.toTypedArray()
                                _lastPurchases.value = date.map { it.marketPurchase }.toTypedArray()
                            }
                            launch {
                                val thisMonthStatistics = date.firstOrNull() { it.month == calendar.month && it.year == calendar.year}
                                _profit.value = thisMonthStatistics?.marketProfit ?: 0
                                _sale.value = thisMonthStatistics?.marketSale ?: 0
                                _purchase.value = thisMonthStatistics?.marketPurchase ?: 0
                            }
                        }
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }
}
