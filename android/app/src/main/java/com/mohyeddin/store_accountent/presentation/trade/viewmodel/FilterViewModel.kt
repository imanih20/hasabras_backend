package com.mohyeddin.store_accountent.presentation.trade.viewmodel

import androidx.lifecycle.ViewModel
import com.mohyeddin.store_accountent.common.Prefs

class FilterViewModel(
    private val prefs: Prefs
) : ViewModel() {

    fun getDate() = prefs.readFilterTradeDate()?:""
    fun getType() = prefs.readFilterTradeType()?:""

    fun saveDate(date: String){
        prefs.writeFilterTradeDate(date)
    }

    fun saveType(type: String){
        prefs.writeFilterTradeType(type)
    }
}