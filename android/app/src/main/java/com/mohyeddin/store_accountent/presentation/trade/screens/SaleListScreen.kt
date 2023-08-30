package com.mohyeddin.store_accountent.presentation.trade.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.datepicker.date.DatePickerDialog
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.domain.product.model.Product
import com.mohyeddin.store_accountent.domain.trade.model.Sale
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.LoadingButton
import com.mohyeddin.store_accountent.presentation.common.components.MyButton
import com.mohyeddin.store_accountent.presentation.common.components.MyDialog
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.components.ShowStatsText
import com.mohyeddin.store_accountent.presentation.common.theme.ADAD_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.KILO_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.TOMAN_TXT
import com.mohyeddin.store_accountent.presentation.trade.components.DeleteDialog
import com.mohyeddin.store_accountent.presentation.trade.components.NumberAdder
import com.mohyeddin.store_accountent.presentation.trade.stateholders.rememberSaleItemState
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.SaleListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.Trash

import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SaleListScreen(viewModel: SaleListViewModel = koinViewModel()) {
    val productList = viewModel.productList.collectAsState()
    val saleList = viewModel.saleList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    MyScaffold(
        topBar = {
            TopAppBar(title = {
                MyText(text = "لیست فروش")
            })
        },
        viewModel = viewModel
    ) {
        SaleListScreenContent(
            saleList = saleList.value,
            productList = productList.value,
            isLoading = isLoading.value,
            addToListAction = {
                viewModel.addSale(
                    it.id,
                    it.title,
                    it.quantity,
                    1.0,
                    it.unit,
                    it.price,
                )
            },
            onSaveBtnClick = {
                viewModel.addSales(saleList.value)
            },
            deleteItem = {
                viewModel.delete(it)
            }
        ){
            viewModel.updateSale(it)
        }

    }
}

@Composable
fun SaleListScreenContent(
    modifier: Modifier = Modifier,
    saleList: List<Sale>,
    productList: List<Product>,
    isLoading: Boolean = false,
    addToListAction: (Product)->Unit,
    onSaveBtnClick: ()->Unit,
    deleteItem: (Sale)->Unit,
    onSaleUpdate: (Sale) -> Unit
) {
    var dialogState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier
            .fillMaxSize()
            .padding(10.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
        ) {
            items(saleList){ sale->
                SaleItem(sale = sale, onDelete = {
                     deleteItem(sale)
                },onSaleUpdate)
                Spacer(modifier = Modifier.size(2.dp))
            }
        }
        Column(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            FloatingActionButton(onClick = { dialogState = true },modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
            Spacer(modifier = Modifier.size(15.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        val style = MaterialTheme.typography.titleLarge
                        MyText(text = "قیمت کل: ",style = style)
                        MyText(
                            saleList
                                .sumOf { it.productPrice * it.quantity }
                                .toInt()
                                .toString()
                                .addNumberSeparator() + " تومان",
                            style = style.copy(color = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    LoadingButton(title = "ثبت نهایی", onClick = onSaveBtnClick, isLoading = isLoading, enabled = saleList.isNotEmpty())
                }
            }
        }
        if(dialogState) MyDialog(onDismissRequest = { dialogState = false }, negativeBtnText = "بستن") {
            if (productList.isEmpty()){
                Box(modifier = Modifier.padding(vertical = 100.dp, horizontal = 40.dp), contentAlignment = Alignment.Center){
                    MyText(text = "محصولی در برنامه ثبت نشده است.")
                }
            }else {
                LazyColumn(Modifier.padding(10.dp)){
                    items(productList){
                        val unit = if(it.unit == "n") ADAD_TXT else KILO_TXT
                        val quantity = if(it.unit == "n") it.quantity.toInt() else it.quantity
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var btnEnabled by remember {
                                mutableStateOf(true)
                            }
                            MyText(text = it.title)
                            MyText(text = "$quantity $unit")
                            Button(
                                onClick = {
                                    addToListAction(it)
                                    btnEnabled = false
                                },
                                enabled = btnEnabled,
                                shape = RoundedCornerShape(10.dp)
                            ){
                                MyText(
                                    if(!btnEnabled)
                                        "اضافه شد"
                                    else
                                        "اضافه کردن",
                                )
                            }
                        }
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun SaleItem(sale: Sale, onDelete:()->Unit, onSaleUpdate: (Sale)->Unit) {
    val state = rememberSaleItemState()
    val dateParts = sale.date.split('-')
    val textStyle = MaterialTheme.typography.bodyLarge
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    ShowStatsText(label = "اسم کالا: ", value = sale.productName )
                    Spacer(modifier = Modifier.size(10.dp))
                    ShowStatsText(label = "قیمت کالا: ", value = sale.productPrice.toString().addNumberSeparator())
                    Spacer(modifier = Modifier.size(10.dp))
                    ShowStatsText(label = "قیمت کل: ",value = (sale.quantity * sale.productPrice).toInt().toString().addNumberSeparator() + " " + TOMAN_TXT)
                }
                Column(horizontalAlignment = Alignment.End) {
                    NumberAdder(number = sale.quantity ,sale.unit,max = sale.productQuantity) {
                        onSaleUpdate(sale.copy(quantity = it))
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(3.dp)) {
                        MyText(text = sale.date,style = textStyle.copy(color = MaterialTheme.colorScheme.secondary))
                        Spacer(modifier = Modifier.size(10.dp))
                        Box(modifier = Modifier
                            .size(20.dp)
                            .clickable { state.datePickerState = true }){
                            Icon(TablerIcons.Calendar, contentDescription = "", tint = MaterialTheme.colorScheme.primary )
                        }
                    }
                }
            }
            Row {
                IconButton(onClick = {
                    state.deleteDialogState = true
                }) {
                    IconBox(imageVector = TablerIcons.Trash, tint = Color.Red)
                }
            }
        }
    }
    if (state.datePickerState) DatePickerDialog(
        onDismissRequest = { state.datePickerState = false },
        onSubmitClicked ={
             onSaleUpdate(sale.copy(date = it))
        },
        initialYear = dateParts[0].toInt(),
        initialMonth = dateParts[1].toInt(),
        initialDay = dateParts[2].toInt()
    )
    if (state.deleteDialogState){
        DeleteDialog(onDismissRequest = { state.deleteDialogState = false }) {
            onDelete()
        }
    }
}





@Preview
@Composable
fun PurchaseListPreview() {
    val saleList = listOf(
        Sale(productId = "", productName = "بوم بوم", productQuantity = 10.0, quantity = 5.0, productPrice = 100000,unit = "n",date = "1401-10-12")
    )

    val productList = listOf(
        Product("","پفک",12000,4.0,2000,"",""),
        Product("","پفک",12000,4.0,2000,"",""),
        Product("","پفک",12000,4.0,2000,"",""),
        Product("","پفک",12000,4.0,2000,"",""),
    )
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        SaleListScreenContent(
            saleList = saleList,
            productList = productList,
            addToListAction = {},
            onSaveBtnClick = {},
            deleteItem = {},
            onSaleUpdate = {}
        )
    }

}