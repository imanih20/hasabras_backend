package com.mohyeddin.store_accountent.presentation.user.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberEditDialogState(name: String,marketName: String) : EditDialogState {
    return remember {
        EditDialogState(name,marketName)
    }
}

class EditDialogState(
    user: String,
    marketName: String
) {
    var userName = mutableStateOf(user)
    var market = mutableStateOf(marketName)
    var isUserInfoChange = mutableStateOf(false)
    var isMarketInfoChange = mutableStateOf(false)

    fun validateUser() : Boolean{
        return isUserInfoChange.value && userName.value.isNotEmpty()
    }
    fun validateMarket() : Boolean{
        return isMarketInfoChange.value && market.value.isNotEmpty()
    }
}