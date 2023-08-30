package com.mohyeddin.store_accountent.presentation.statistic.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder

@Composable
fun rememberStatisticState() : StatisticContentStateHolder{
    return remember {
        StatisticContentStateHolder(StatisticContentStateHolder.Tabs.USER)
    }
}

class StatisticContentStateHolder(defaultTab: Tabs,) {
    enum class Tabs(val value: String){
        USER("شما"),
        SHAREHOLDERS("سهامداران"),
        MARKET("فروشگاه")
    }

    var selectedShareholder = mutableStateOf<String>("")

    var selectedTab = mutableStateOf(defaultTab)

    val isShareholderTab : State<Boolean>
        get() = derivedStateOf {
        selectedTab.value == Tabs.SHAREHOLDERS
    }

}