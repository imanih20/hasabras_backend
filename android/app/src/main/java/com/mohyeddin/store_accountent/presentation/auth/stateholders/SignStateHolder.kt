package com.mohyeddin.store_accountent.presentation.auth.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mohyeddin.store_accountent.common.withEnglishNumber

@Composable
fun rememberSignState() : SignStateHolder{
    return remember{
        SignStateHolder()
    }
}
class SignStateHolder {
    var name = mutableStateOf("")
    var phone = mutableStateOf("")
    val isPhoneValid = mutableStateOf(true)
    val isNameValid = mutableStateOf(true)
    val phoneError = mutableStateOf("")
    val nameError = mutableStateOf("")

    fun validatePhone(){
        if(!phone.value.withEnglishNumber().startsWith("09")){
            isPhoneValid.value = false
            phoneError.value = "شماره تلفن حتما باید با ۰۹ شروع شود."
        }
        else if(phone.value.length!=11){
            isPhoneValid.value = false
            phoneError.value = "شماره تلفن باید ۱۱ عدد باشد."
        }else{
            isPhoneValid.value = true
            phoneError.value = ""
        }
    }

    fun validateName(){
        if(name.value.trim().isEmpty()){
            isNameValid.value = false
            nameError.value = "نام نمی تواند خالی باشد."
        }else{
            isNameValid.value = true
            nameError.value = ""
        }
    }
}