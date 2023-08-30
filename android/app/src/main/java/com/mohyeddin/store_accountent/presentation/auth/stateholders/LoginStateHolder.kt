package com.mohyeddin.store_accountent.presentation.auth.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mohyeddin.store_accountent.common.withEnglishNumber


@Composable
fun rememberViewModelState() : LoginStateHolder{
    return remember {
        LoginStateHolder()
    }
}
class LoginStateHolder() {
    val internetDialog = mutableStateOf(false)
    val isPhoneValid = mutableStateOf(true)
    val validateMessage = mutableStateOf("")
    val phone = mutableStateOf("")

    fun validatePhone(){
        if(!phone.value.withEnglishNumber().startsWith("09")){
            isPhoneValid.value = false
            validateMessage.value = "شماره تلفن حتما باید با ۰۹ شروع شود."
        }
        else if(phone.value.length!=11){
            isPhoneValid.value = false
            validateMessage.value = "شماره تلفن باید ۱۱ عدد باشد."
        }else{
            isPhoneValid.value = true
            validateMessage.value = ""
        }
    }
}