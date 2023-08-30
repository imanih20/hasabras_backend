package com.mohyeddin.store_accountent.presentation.statistic.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.domain.shareholder.usecase.GetAllShareholdersUseCase
import com.mohyeddin.store_accountent.domain.statistic.models.Statistic
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetMarketStatisticsUseCase
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetShareholderStatistics
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.math.log

class StatisticViewModel(
    private val getShareholders: GetAllShareholdersUseCase,
    private val getShareholderState: GetShareholderStatistics,
    private val getMarketState: GetMarketStatisticsUseCase
) : BaseViewModel(){


    init {
        fetchStatistics(StatisticContentStateHolder.Tabs.USER)
    }

    private val _purchaseList = MutableStateFlow<List<Int>>(emptyList())
    private val _saleList = MutableStateFlow<List<Int>>(emptyList())
    private val _profitList = MutableStateFlow<List<Int>>(emptyList())
    private val _dateList = MutableStateFlow<List<String>>(emptyList())

    val purchaseList : StateFlow<List<Int>> = _purchaseList
    val saleList : StateFlow<List<Int>> = _saleList
    val profitList : StateFlow<List<Int>> = _profitList
    val dateList : StateFlow<List<String>> = _dateList
    init {
        fetchShareholders()
    }

    private fun initiateLists(list: List<Statistic>){
        Log.i("statistic_tag",list.joinToString())
        _purchaseList.value = list.map { it.purchase }
        _saleList.value = list.map { it.sale }
        _profitList.value = list.map { it.profit }
        _dateList.value = list.map{"${it.year}-${it.month}"}
    }

    private val _shareholders = MutableStateFlow<List<Shareholder>>(emptyList())
    val shareholders : StateFlow<List<Shareholder>> get() = _shareholders


    private fun fetchShareholders(){
        viewModelScope.launch {
            setLoading()
            getShareholders()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> _shareholders.emit(it.data)
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }

    private suspend fun getShareholderStatistic(shareholderId: String){
        setLoading()
        getShareholderState(shareholderId)
            .catch {
                hideLoading()
                showSnackBar(SnackBarLevel.Error,it.message.toString())
            }
            .collect{ baseResult ->
                hideLoading()
                when(baseResult){
                    is BaseResult.Success-> {
                        initiateLists(baseResult.data.map { Statistic(it.sale,it.purchase,it.profit,it.month,it.year) })
                    }
                    is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                }
            }

    }

    private suspend fun getMarketStatistic(selected: StatisticContentStateHolder.Tabs){
        setLoading()
        getMarketState()
            .catch {
                hideLoading()
                showSnackBar(SnackBarLevel.Error,it.message.toString())
            }
            .collect{ baseResult ->
                hideLoading()
                when(baseResult){
                    is BaseResult.Success-> {
                        val list = baseResult.data.map {
                            if (selected == StatisticContentStateHolder.Tabs.USER){
                                Statistic(it.ownerSale,it.ownerPurchase,it.ownerProfit,it.month,it.year)
                            }else {
                                Statistic(it.marketSale,it.marketPurchase,it.marketProfit,it.month,it.year)
                            }
                        }
                        initiateLists(list)
                    }
                    is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                }
            }

    }

    fun fetchStatistics(selected: StatisticContentStateHolder.Tabs,shareholderId: String = ""){
        Log.i("statistic",selected.value+" shid:"+shareholderId)
        viewModelScope.launch {
            if (selected == StatisticContentStateHolder.Tabs.SHAREHOLDERS){
                getShareholderStatistic(shareholderId)
            }else{
                getMarketStatistic(selected)
            }
        }
    }
}
