package com.mohyeddin.store_accountent.presentation.trade.viewmodel

import android.app.DownloadManager.Request
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.common.checkInternet
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.repository.PurchaseRepository
import com.mohyeddin.store_accountent.domain.trade.usecase.AddPurchasesUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PurchaseListViewModel(
    private val purchaseRepo: PurchaseRepository,
    private val addPurchases: AddPurchasesUseCase,
) : BaseViewModel() {



    private val _purchaseList = MutableStateFlow<List<Purchase>>(emptyList())
    val purchaseList: StateFlow<List<Purchase>> get() = _purchaseList

    init {
        getPurchases()
    }


    private fun getPurchases(){
        viewModelScope.launch {
            purchaseRepo.getAll().collect{
                _purchaseList.value = it
            }
        }
    }

    fun deletePurchase(purchase: Purchase){
        viewModelScope.launch {
            purchaseRepo.delete(purchase)
        }
    }
    fun updatePurchase(purchase: Purchase){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseRepo.update(purchase)
            showSnackBar(SnackBarLevel.Success,"با موفقیت ذخیره شده.")
        }
    }

    fun savePurchases(list: List<Purchase>){
        setLoading()
        checkListRequest(
            request = {
                addPurchases(list)
            }
        ) {base->
            hideLoading()
            when(base){
                is BaseResult.Success -> {
                    showSnackBar(SnackBarLevel.Success,"با موفقیت ذخیره شده.")
                    purchaseRepo.deleteAll()
                }
                is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
            }
        }
    }
}