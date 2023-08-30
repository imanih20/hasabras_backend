package com.mohyeddin.store_accountent.presentation.statistic.viewmodels

import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic
import com.mohyeddin.store_accountent.domain.statistic.usecase.GetShareholderStatistics
import com.mohyeddin.store_accountent.domain.statistic.usecase.UpdateIsPaidUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ShareholderStatisticViewModel(
    private val getStatistics: GetShareholderStatistics,
    private val updateIsPaid: UpdateIsPaidUseCase,
    private val shareholderId: String
) : BaseViewModel() {
    private val _statistics = MutableStateFlow<List<MonthStatistic>>(emptyList())
    val statistics get() = _statistics

    init {
        fetchStatistics(shareholderId)
    }
    private fun fetchStatistics(shareholderId: String){
        viewModelScope.launch {
            setLoading()
            getStatistics(shareholderId)
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> _statistics.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطای ناشناخته")
                    }
                }
        }
    }

    fun update(statisticId: String,isPaid:Boolean){
        checkRequest(
            {
               updateIsPaid(statisticId,isPaid)
            }
        ){
            hideLoading()
            when(it){
                is BaseResult.Success -> fetchStatistics(shareholderId)
                is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,it.rawResponse.message)
            }
        }
    }

}