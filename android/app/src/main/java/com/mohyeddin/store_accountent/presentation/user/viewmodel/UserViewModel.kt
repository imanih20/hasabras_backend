package com.mohyeddin.store_accountent.presentation.user.viewmodel

import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.market.model.Market
import com.mohyeddin.store_accountent.domain.market.usecase.GetMarketUseCase
import com.mohyeddin.store_accountent.domain.market.usecase.UpdateMarketUseCase
import com.mohyeddin.store_accountent.domain.user.UserRepository
import com.mohyeddin.store_accountent.domain.user.model.User
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepo: UserRepository,
    private val getMarket: GetMarketUseCase,
    private val updateMarket: UpdateMarketUseCase
) : BaseViewModel() {

    private val _market = MutableStateFlow<Market?>(null)
    val market: StateFlow<Market?> get()= _market

    private val _user = MutableStateFlow<User?>(null)
    val user : StateFlow<User?> get()= _user

    init {
        getUserInfo()
        getMarketInfo()
    }

    private fun getMarketInfo(){
        viewModelScope.launch {
            setLoading()
            getMarket()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect {
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> _market.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,it.rawResponse.message)
                    }
                }
        }
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            userRepo.getMe()
                .onStart { setLoading() }
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> _user.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطا در ارتباط")
                    }
                }
        }
    }

    fun updateUser(name: String){
        setLoading()
        checkRequest(
            request = {
                userRepo.update(name)
            }
        ) {
            hideLoading()
            when(it){
                is BaseResult.Success -> {
                    _user.value = it.data as User
                    showSnackBar(SnackBarLevel.Success,"اطلاعات شما با موفقیت بروزرسانی شد")
                }
                is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطا در ارتباط")
            }
        }
    }

    fun updateMarketInfo(name: String){
        setLoading()
        checkRequest (
            request = {
                updateMarket(name)
            }
        ){
            hideLoading()
            when(it){
                is BaseResult.Success -> {
                    _market.value = it.data as Market
                    showSnackBar(SnackBarLevel.Success,"اطلاعات شما با موفقیت بروزرسانی شد")
                }
                is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطا در ارتباط")
            }
        }
    }

}

