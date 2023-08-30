package com.mohyeddin.store_accountent.presentation.trade.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mohyeddin.store_accountent.common.withEnglishNumber
import ir.huri.jcal.JalaliCalendar

@Composable
fun rememberAddPurchaseState(calendar: JalaliCalendar = JalaliCalendar()) : AddPurchaseStateHolder {
    return remember {
        AddPurchaseStateHolder(calendar)
    }
}

class AddPurchaseStateHolder(
    calendar: JalaliCalendar
) {
    val datePickerState = mutableStateOf(false)

    val title = mutableStateOf("")
    val titleError = mutableStateOf("")
    val isTitleError = mutableStateOf(false)

    val productId = mutableStateOf("")

    val quantity  = mutableStateOf("")
    val quantityError = mutableStateOf("")
    val isQuantityError = mutableStateOf(false)

    val unit = mutableStateOf("n")

    val totalPurchasePrice = mutableStateOf("")
    val totalError = mutableStateOf("")
    val isTotalError = mutableStateOf(false)

    val salePrice = mutableStateOf("")
    val saleError = mutableStateOf("")
    val isSaleError = mutableStateOf(false)

    val ownerId = mutableStateOf("")

    val ownerName = mutableStateOf("")

    val date = mutableStateOf(calendar.toString())
    val dateError = mutableStateOf("")
    val isDateError = mutableStateOf(false)
    private fun validateSalePrice() : Boolean {
        if(salePrice.value.isEmpty()){
            isSaleError.value = true
            saleError.value = "قیمت فروش نمی تواند خالی یا کوچکتر از قیمت خرید هر کالا باشد."
        }
        if (salePrice.value.withEnglishNumber().toInt()<(totalPurchasePrice.value.withEnglishNumber().toInt()/quantity.value.withEnglishNumber().toDouble())){
            isSaleError.value = true
            saleError.value = "قیمت فروش نمی تواند خالی یا کوچکتر از قیمت خرید هر کالا باشد."
        }
        else {
            isSaleError.value = false
            saleError.value = ""
        }
        return !isSaleError.value
    }

    private fun validateTotalPurchasePrice() : Boolean {
        if (totalPurchasePrice.value.isEmpty()){
            isTotalError.value = true
            totalError.value = "قیمت کل خرید نمی تواند خالی یا صفر باشد."
        }
        if (totalPurchasePrice.value.withEnglishNumber().toInt()<=0) {
            isTotalError.value = true
            totalError.value = "قیمت کل خرید نمی تواند خالی یا صفر باشد."
        }
        else {
            isTotalError.value = false
            totalError.value = ""
        }
        return !isTotalError.value
    }
    private fun validateQuantity() : Boolean {
        if (quantity.value.isEmpty()){
            isQuantityError.value = true
            quantityError.value = "تعداد نمی تواند خالی یا صفر باشد."
        }
        if (quantity.value.withEnglishNumber().toInt()<=0){
            isQuantityError.value = true
            quantityError.value = "تعداد نمی تواند خالی یا صفر باشد."
        }
        else {
            isQuantityError.value = false
            quantityError.value = ""
        }
        return !isQuantityError.value
    }

    private fun validateTitle() : Boolean {
        if (title.value.isEmpty()) {
            isTitleError.value = true
            titleError.value = "اسم کالا نمی تواند خالی باشد"
        }
        else {
            isTitleError.value = false
            titleError.value = ""
        }
        return !isTitleError.value
    }

    private fun validateDate() : Boolean {
        if (date.value.isEmpty()){
            isDateError.value = true
            dateError.value = "تاریخ اشتباه است."
        }
        if (date.value.split('-').size != 3) {
            isDateError.value = true
            dateError.value = "تاریخ اشتباه است."
        }
        else {
            isDateError.value = false
            dateError.value = ""
        }
        return !isDateError.value
    }

    fun validateInput() : Boolean{
        return validateDate()&&validateQuantity()&&validateSalePrice()&&validateTitle()&&validateTotalPurchasePrice()
    }
}