package com.mohyeddin.store_accountent.presentation.common.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.data.common.exceptions.NetworkNotConnectedException
import com.mohyeddin.store_accountent.data.common.utils.WrappedListResponse
import com.mohyeddin.store_accountent.data.common.utils.WrappedResponse
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.random.Random

open class BaseViewModel : ViewModel() {
    private val _showInternetDialog : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _snackBarLevel = MutableStateFlow(SnackBarLevel.Info)
    val snackBarLevel get() = _snackBarLevel.asStateFlow()

    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage get() = _snackBarMessage.asStateFlow()
    private val _snackId = MutableStateFlow(0.0)
    val snackId get()=_snackId.asStateFlow()

    val isLoading : StateFlow<Boolean> get() = _isLoading

    fun showSnackBar(level: SnackBarLevel,message: String){
        _snackBarLevel.value = level
        _snackBarMessage.value = message
        _snackId.value = Math.random()
    }

    protected fun setLoading(){
        _isLoading.value = true
    }
    protected fun hideLoading(){
        _isLoading.value = false
    }

    val showInternetDialog get() = _showInternetDialog.asStateFlow()
    fun setShowInternetDialog(boolean: Boolean){
        _showInternetDialog.value = boolean
    }

    fun checkRequest(request:suspend ()->Flow<BaseResult<*,WrappedResponse<*>>>,collect:FlowCollector<BaseResult<*,WrappedResponse<*>>>){
        viewModelScope.launch {
            try {
                request()
                    .catch {
                        hideLoading()
                        if (it.cause == NetworkNotConnectedException()){
                            setShowInternetDialog(true)
                        }
                        showSnackBar(SnackBarLevel.Error,it.message.toString())
                    }
                    .collect(collect)
            }catch (e:NetworkNotConnectedException){
                setShowInternetDialog(true)
                hideLoading()
            }catch (e:Exception){
                hideLoading()
                showSnackBar(SnackBarLevel.Error,e.message.toString())
            }

        }
    }
    fun checkListRequest(request:suspend ()->Flow<BaseResult<*,WrappedListResponse<*>>>,collect:FlowCollector<BaseResult<*,WrappedListResponse<*>>>){
        viewModelScope.launch {
            try {
                request()
                    .catch {
                        hideLoading()
                        if (it.cause == NetworkNotConnectedException()){
                            setShowInternetDialog(true)
                        }
                        showSnackBar(SnackBarLevel.Error,it.message.toString())
                    }
                    .collect(collect)
            }catch (e:NetworkNotConnectedException){
                hideLoading()
                setShowInternetDialog(true)
            }catch (e: Exception) {
                showSnackBar(SnackBarLevel.Error,e.message.toString())
                Log.e("error",e.message,e.cause)
                hideLoading()
            }

        }
    }
}