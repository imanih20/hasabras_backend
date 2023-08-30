package com.mohyeddin.store_accountent.presentation.auth.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun rememberVerifyState(scope: CoroutineScope) : VerifyStateHolder {
    return remember{
        VerifyStateHolder(scope)
    }
}
class VerifyStateHolder(private val scope: CoroutineScope) {
    val currentTimerTime = mutableStateOf(120)
    val codeError = mutableStateOf("")
    val isCodeError = mutableStateOf(false)
    val code = mutableStateOf("")

    init {
        timer()
    }
    fun setError(message: String){
        codeError.value = message
        isCodeError.value = true
    }

    fun hidError(){
        codeError.value = ""
        isCodeError.value = false
    }

    private fun timer(){
        scope.launch {
            while (currentTimerTime.value > 0){
                delay(1000)
                currentTimerTime.value --
            }
        }
    }
}