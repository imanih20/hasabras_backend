package com.mohyeddin.store_accountent.presentation.trade.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.product.model.Product
import com.mohyeddin.store_accountent.domain.product.usecase.GetProductListUseCase
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import com.mohyeddin.store_accountent.domain.trade.repository.SaleRepository
import com.mohyeddin.store_accountent.domain.trade.usecase.AddSalesUseCase
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SaleListViewModel(
    private val saleRepository: SaleRepository,
    private val getProductList: GetProductListUseCase,
    private val addListOfSales: AddSalesUseCase,
    private val calender: JalaliCalendar = JalaliCalendar()
) : BaseViewModel(){

    private val _saleList = MutableStateFlow<List<Sale>>(emptyList())
    val saleList : StateFlow<List<Sale>> get() = _saleList

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> get() = _productList

    init {
        getSaleList()
        getProducts()
    }

    private fun getSaleList(){
        viewModelScope.launch {
            saleRepository.getAll()
                .collect{
                    _saleList.value = it
                }
        }
    }

    fun addSale(
        productId: String,
        productName: String,
        productQuantity: Double,
        quantity: Double,
        unit: String,
        productPrice: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            saleRepository
                .insert(
                    Sale(
                        productId = productId,
                        productName = productName,
                        productQuantity = productQuantity,
                        quantity = quantity,
                        unit = unit,
                        productPrice = productPrice,
                        date = calender.toString()
                    )
                )
        }
    }
    fun delete(vararg sale: Sale){
        viewModelScope.launch(Dispatchers.IO) {
            saleRepository.delete(*sale)
        }
    }

    fun updateSale(sale: Sale){
        viewModelScope.launch(Dispatchers.IO) {
            saleRepository.update(sale)
            showSnackBar(SnackBarLevel.Success,"با موفقیت ذخیره شد.")
        }
    }

    private fun getProducts(){
        viewModelScope.launch {
            setLoading()
            getProductList()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{result->
                    hideLoading()
                    when(result){
                        is BaseResult.Success -> {
                            _productList.value = result.data.filter { it.quantity > 0 }
                        }
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }
    fun addSales(saleList: List<Sale>){
        setLoading()
        checkListRequest(
            request = {
                addListOfSales(saleList)
            }
        ){ baseResult->
            hideLoading()
            when(baseResult){
                is BaseResult.Success -> {
                    showSnackBar(SnackBarLevel.Success,"با موفقیت ذخیره شد.")
                    saleRepository.deleteAll()
                }
                is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
            }
        }
    }
}
