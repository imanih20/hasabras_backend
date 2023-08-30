package com.mohyeddin.store_accountent.presentation.trade.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.product.model.Product
import com.mohyeddin.store_accountent.domain.product.usecase.GetProductListUseCase
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.domain.shareholder.usecase.GetAllShareholdersUseCase
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.domain.trade.repository.PurchaseRepository
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddPurchaseViewModel(
    private val getAllShareholders : GetAllShareholdersUseCase,
    private val getProductList: GetProductListUseCase,
    private val purchaseRepo: PurchaseRepository
) : BaseViewModel() {


    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList : StateFlow<List<Product>> get() = _productList

    private val _shareholderList = MutableStateFlow<List<Shareholder>>(emptyList())
    val shareholderList : StateFlow<List<Shareholder>> get() = _shareholderList

    init {
        getShareholders()
        getProducts()
    }


    private fun getProducts(){
        viewModelScope.launch {
            setLoading()
            getProductList()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> _productList.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }

    private fun getShareholders(){
        viewModelScope.launch {
            setLoading()
            getAllShareholders()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success -> _shareholderList.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }

    fun addPurchase(
        productName: String,
        productId: String,
        ownerName: String,
        ownerId: String,
        quantity: Double,
        unit: String,
        totalPurchasePrice: Int,
        salePrice: Int,
        date: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseRepo.insert(Purchase(
                pid = productId,
                title = productName,
                quantity = quantity,
                totalPrice = totalPurchasePrice,
                unit = unit,
                salePrice = salePrice,
                ownerId = ownerId,
                ownerName = ownerName,
                date = date
            ))
        }
    }
}
