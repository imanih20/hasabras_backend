package com.mohyeddin.store_accountent.presentation.trade.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ir.huri.jcal.JalaliCalendar


@Composable
fun rememberFilterScreenState(
    date: String,
    type: String
):FilterScreenStateHolder {
    return remember {
        FilterScreenStateHolder(date,type)
    }
}

class FilterScreenStateHolder(
    defaultDate: String,
    defaultType: String
) {
    var date by mutableStateOf(defaultDate)
    var type by mutableStateOf(defaultType)
    var isDateCheck by mutableStateOf(date.isEmpty())
    var datePickerState by mutableStateOf(false)

    fun toggleCheck(check: Boolean){
        isDateCheck = check
        if (isDateCheck){
            date = ""
        }else {
            date = JalaliCalendar().toString()
        }
    }

}