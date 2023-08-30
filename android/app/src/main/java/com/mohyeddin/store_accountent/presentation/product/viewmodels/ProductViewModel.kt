package com.mohyeddin.store_accountent.presentation.product.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohyeddin.store_accountent.common.withPersianNumbers
import com.mohyeddin.store_accountent.domain.common.BaseResult
import com.mohyeddin.store_accountent.domain.product.model.Product
import com.mohyeddin.store_accountent.domain.product.usecase.GetProductListUseCase
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import com.mohyeddin.store_accountent.domain.trade.repository.SaleRepository
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getAllProducts: GetProductListUseCase,
    private val saleRepository: SaleRepository,
    private val calendar: JalaliCalendar
) : BaseViewModel(){

    private val _state = MutableStateFlow<ProductScreenState>(ProductScreenState.Init)
    val state : StateFlow<ProductScreenState> get() = _state

    private val addToDbCount = MutableStateFlow(0)

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products : StateFlow<List<Product>> get() = _products

    init {
        getProducts()
    }


    private fun getProducts() {
        viewModelScope.launch {
            setLoading()
            getAllProducts()
                .catch {
                    hideLoading()
                    showSnackBar(SnackBarLevel.Error,it.message.toString())
                }
                .collect{
                    hideLoading()
                    when(it){
                        is BaseResult.Success-> _products.value = it.data
                        is BaseResult.Error -> showSnackBar(SnackBarLevel.Error,"خطایی رخ داده است.")
                    }
                }
        }
    }

    fun addSaleToDb(product: Product){
        viewModelScope.launch {
            saleRepository.insert(sale = Sale(
                productId = product.id,
                date = calendar.toString(),
                productName = product.title,
                productQuantity = product.quantity,
                quantity = 1.0,
                productPrice = product.price,
                unit = product.unit
            ))
            addToDbCount.value++
            showSnackBar(SnackBarLevel.Success,"${addToDbCount.value} کالا به لیست اضافه شد.".withPersianNumbers())
        }
    }

}

sealed class ProductScreenState{
    object Init: ProductScreenState()
    data class ShowToast(val message: String) : ProductScreenState()
}