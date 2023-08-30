package com.mohyeddin.store_accountent.presentation.trade.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun rememberPurchaseItemState() : PurchaseItemStateHolder {
    return remember {
        PurchaseItemStateHolder()
    }
}
class PurchaseItemStateHolder {
    var datePickerState by mutableStateOf(false)
    var editDialogState by mutableStateOf(false)
    var deleteDialogState by mutableStateOf(false)
}