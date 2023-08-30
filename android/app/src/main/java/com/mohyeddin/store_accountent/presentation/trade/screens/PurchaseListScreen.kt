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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.datepicker.date.DatePickerDialog
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.domain.trade.model.Purchase
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.LoadingButton
import com.mohyeddin.store_accountent.presentation.common.components.MyButton
import com.mohyeddin.store_accountent.presentation.common.components.MyDialog
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.ShowStatsText
import com.mohyeddin.store_accountent.presentation.common.theme.ADAD_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.KILO_TXT
import com.mohyeddin.store_accountent.presentation.common.utils.PriceVisualTransformation
import com.mohyeddin.store_accountent.presentation.destinations.AddPurchaseScreenDestination
import com.mohyeddin.store_accountent.presentation.trade.components.DeleteDialog
import com.mohyeddin.store_accountent.presentation.trade.stateholders.rememberPurchaseItemState
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.PurchaseListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.Edit
import compose.icons.tablericons.Trash
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PurchaseListScreen(navigator: DestinationsNavigator,viewModel: PurchaseListViewModel = koinViewModel()) {
    val purchaseList = viewModel.purchaseList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()



    MyScaffold(
        topBar = {
            TopAppBar(title = {
                MyText(text = "لیست خرید")
            })
        },
        viewModel = viewModel
    ) {
        PurchaseListScreenContent(
            purchaseList = purchaseList.value,
            onSaveBtnClick = {
                viewModel.savePurchases(purchaseList.value)
            },
            isLoading = isLoading.value,
            onNavToAddPurchase = {navigator.navigate(AddPurchaseScreenDestination)},
            deleteItem = {
                viewModel.deletePurchase(it)
            }
        ){
            viewModel.updatePurchase(it)
        }
    }
}

@Composable
fun PurchaseListScreenContent(
    modifier: Modifier = Modifier,
    purchaseList: List<Purchase>,
    isLoading: Boolean = false,
    onSaveBtnClick: ()->Unit,
    onNavToAddPurchase: ()->Unit,
    deleteItem: (Purchase)->Unit,
    onUpdate: (Purchase)->Unit
) {
    Box(
        modifier
            .padding(10.dp)
            .fillMaxSize()) {
        LazyColumn {
            items(purchaseList){ purchase ->
                PurchaseItem(
                    purchase = purchase,
                    onDelete = {
                        deleteItem(it)
                    },
                    onUpdate = onUpdate)
                Spacer(modifier = Modifier.size(2.dp))
            }
        }
        Column(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            FloatingActionButton(onClick = onNavToAddPurchase,modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
            Spacer(modifier = Modifier.size(15.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        val style = MaterialTheme.typography.titleLarge
                        MyText(text = "قیمت کل: ",style = style)
                        MyText(
                            purchaseList
                                .sumOf { it.totalPrice }
                                .toString()
                                .addNumberSeparator() + " تومان",
                            style = style.copy(color = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    LoadingButton(title = "ثبت نهایی", onClick = { onSaveBtnClick() }, isLoading = isLoading, enabled = purchaseList.isNotEmpty())
                }
            }
        }

    }
}

@Composable
fun PurchaseItem(purchase: Purchase,onDelete:(Purchase)->Unit,onUpdate : (Purchase)->Unit) {
    val  quantity by remember {
        mutableStateOf(
            if (purchase.unit == "n")
                "${purchase.quantity.toInt()} $ADAD_TXT"
            else
                "${purchase.quantity} $KILO_TXT"
        )
    }
    val unit by  remember {
        mutableStateOf(if (purchase.unit == "n")  "تعداد: " else "مقدار: ")
    }
    val state = rememberPurchaseItemState()
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier) {
                    ShowStatsText(label = "اسم کالا: ", value = purchase.title)
                    Spacer(modifier = Modifier.size(10.dp))
                    ShowStatsText(label = "قیمت کل خرید: ", value = purchase.totalPrice.toString().addNumberSeparator() )
                    Spacer(modifier = Modifier.size(10.dp))
                    ShowStatsText(label = unit, value = quantity)
                    Spacer(modifier = Modifier.size(10.dp))
                    ShowStatsText(
                        label = "قیمت فروش: ",
                        value = purchase.salePrice.toString().addNumberSeparator()
                    )
                    if (purchase.ownerName.isNotEmpty()){
                        Spacer(modifier = Modifier.size(10.dp))
                        ShowStatsText(label = "سهم بر: ", value = purchase.ownerName)
                    }
                }
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.align(Alignment.Bottom)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(3.dp)) {
                        MyText(text = purchase.date,style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary))
                        Spacer(modifier = Modifier.size(10.dp))
                        Box(modifier = Modifier
                            .size(20.dp)
                            .clickable { state.datePickerState = true }){
                            IconBox(TablerIcons.Calendar, tint = MaterialTheme.colorScheme.primary )
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
                IconButton(onClick = {
                    state.editDialogState = true
                }) {
                    IconBox(imageVector = TablerIcons.Edit, tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
    val dateParts = purchase.date.split('-')
    if (state.datePickerState) DatePickerDialog(
        onDismissRequest = { state.datePickerState = false },
        onSubmitClicked ={
            onUpdate(purchase.copy(date = it))
        },
        initialYear = dateParts[0].toInt(),
        initialMonth = dateParts[1].toInt(),
        initialDay = dateParts[2].toInt()
    )
    if (state.deleteDialogState){
        DeleteDialog(onDismissRequest = {
            state.deleteDialogState = false
        }) {
            onDelete(purchase)
        }
    }
    if (state.editDialogState){
        EditPurchaseDialog(onDismissRequest = {
            state.editDialogState = false
        }, onPosClick = {
            onUpdate(it)
        }, purchase = purchase )
    }
}


@Composable
fun EditPurchaseDialog(
    onDismissRequest:()->Unit,
    onPosClick:(Purchase)->Unit,
    purchase: Purchase
) {
    var totalPurchasePrice by remember {
        mutableStateOf(purchase.totalPrice.toString())
    }
    var salePrice by remember {
        mutableStateOf(purchase.salePrice.toString())
    }
    var quantity by remember {
        mutableStateOf(purchase.quantity.toString())
    }
    MyDialog(
        onDismissRequest = onDismissRequest,
        onPositiveBtnClick = {
            onPosClick(
                purchase
                    .copy(
                        totalPrice = totalPurchasePrice.withEnglishNumber().toInt(),
                        salePrice = salePrice.withEnglishNumber().toInt(),
                        quantity = quantity.withEnglishNumber().toDouble()
                    )
            )
        },
        positiveBtnTxt = "ثبت",
        negativeBtnText = "لغو"
    ) {
        Column(Modifier.padding(10.dp)) {
            MyInputTextField(
                value = totalPurchasePrice,
                onValueChange = {totalPurchasePrice = it},
                label = "قیمت کل",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = {
                    PriceVisualTransformation().filter(it)
                },
            )
            MyInputTextField(
                value = salePrice,
                onValueChange = {salePrice = it},
                label = "قیمت فروش",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = {
                    PriceVisualTransformation().filter(it)
                },
            )
            MyInputTextField(
                value = quantity,
                onValueChange = {quantity = it},
                label = "تعداد",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )
        }
    }
}
@Preview
@Composable
fun SaleListPreview() {
    val purchaseList = listOf(
        Purchase(0,"","ماست",10.0,12000,"n",1000,"","","1402-4-3")
    )
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        PurchaseListScreenContent(
            purchaseList = purchaseList,
            onSaveBtnClick = {} ,
            onNavToAddPurchase = {},
            deleteItem = {}
        ){}
    }
}