package com.mohyeddin.store_accountent.presentation.trade.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.datepicker.date.DatePickerDialog
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.domain.product.model.Product
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.presentation.common.components.AutoCompleteTextFiled
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.MyButton
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.utils.DateVisualTransformation
import com.mohyeddin.store_accountent.presentation.common.utils.PriceVisualTransformation
import com.mohyeddin.store_accountent.presentation.trade.stateholders.AddPurchaseStateHolder
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.AddPurchaseViewModel
import com.mohyeddin.store_accountent.presentation.trade.stateholders.rememberAddPurchaseState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar

import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun AddPurchaseScreen(navigator: DestinationsNavigator,viewModel: AddPurchaseViewModel = koinViewModel()) {
    val productList = viewModel.productList.collectAsState()
    val shareholderList = viewModel.shareholderList.collectAsState()

    val contentState = rememberAddPurchaseState()
    MyScaffold(
        viewModel,
        bottomBar = {
            MyButton(
                title = "اضافه به لیست",
                onclick = {
                    if (contentState.validateInput()){
                        viewModel.addPurchase(
                            contentState.title.value,
                            contentState.productId.value,
                            contentState.ownerName.value,
                            contentState.ownerId.value,
                            contentState.quantity.value.withEnglishNumber().toDouble(),
                            contentState.unit.value,
                            contentState.totalPurchasePrice.value.withEnglishNumber().toInt(),
                            contentState.salePrice.value.withEnglishNumber().toInt(),
                            contentState.date.value.withEnglishNumber()
                        )
                        navigator.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    ) {
        AddPurchaseScreenContent(
            state = contentState,
            productList = productList.value,
            shareholderList = shareholderList.value
        )
    }
}

@Composable
fun AddPurchaseScreenContent(
    modifier : Modifier = Modifier,
    state: AddPurchaseStateHolder = rememberAddPurchaseState(),
    productList : List<Product>,
    shareholderList: List<Shareholder>
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        AutoCompleteTextFiled(
            value = state.title.value ,
            onValueChange = {id,text->
                state.productId.value= id
                state.title.value = text
            },
            productList.associate { Pair(it.id,it.title) },
            label = "اسم کالا",
            isError = state.isTitleError.value,
            supportingText = state.titleError.value,
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(Modifier.fillMaxWidth()) {
            MyInputTextField(
                value = state.quantity.value,
                onValueChange = {state.quantity.value = it},
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = if (state.unit.value == "n") "تعداد" else "مقدار",
                isError = state.isQuantityError.value,
                supportingText = state.quantityError.value
            )
            Spacer(modifier = Modifier.size(10.dp))
            AutoCompleteTextFiled(
                value = "عدد",
                onValueChange = {id,_->
                    state.unit.value = id
                },
                modifier = Modifier.weight(1f),
                suggestionList = mapOf(
                    Pair("n","عدد"),
                    Pair("w","کیلو")
                ),
                selectOnly = true,
                label = "واحد"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        MyInputTextField(
            value = state.totalPurchasePrice.value,
            onValueChange = {state.totalPurchasePrice.value = it},
            label = "قیمت کل",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = {
                PriceVisualTransformation().filter(it)
            },
            isError = state.isTotalError.value,
            supportingText = state.totalError.value
        )
        Spacer(modifier = Modifier.size(10.dp))
        MyInputTextField(
            state.salePrice.value,
            onValueChange = {state.salePrice.value = it},
            label = "قیمت فروش",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = {
                PriceVisualTransformation().filter(it)
            },
            isError = state.isSaleError.value,
            supportingText = state.saleError.value
        )
        Spacer(modifier = Modifier.size(10.dp))
        if (shareholderList.isNotEmpty())AutoCompleteTextFiled(
            value = state.ownerName.value,
            onValueChange = {id,text->
                state.ownerId.value = id
                state.ownerName.value = text
            },
            suggestionList = shareholderList.associate { Pair(it.id,it.name) },
            selectOnly = true,
            label = "سود بر"
        )
        Spacer(modifier = Modifier.size(10.dp))
        MyInputTextField(
            value = state.date.value.filterNot { it == '-' },
            onValueChange = {
                state.date.value = it
            },
            trailingIcon = {
                Box(modifier = Modifier
                    .size(22.dp)
                    .clickable {
                        state.datePickerState.value = true
                    }){
                    IconBox(imageVector = TablerIcons.Calendar)
                }
            },
            label = "تاریخ خرید",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = {
                DateVisualTransformation().filter(it)
            },
            isError = state.isDateError.value,
            supportingText = state.dateError.value
        )
    }
    if (state.datePickerState.value) DatePickerDialog(onDismissRequest = { state.datePickerState.value = false }, onSubmitClicked = {state.date.value = it})

}

@Preview
@Composable
fun AddPurchasePreview() {

    val productList = listOf(
        Product("1","ز",1000,10.0,100,"",""),
        Product("2","بوم بوم",1000,10.0,100,"",""),
        Product("3","ماست",1000,10.0,100,"",""),
        Product("4","پفک چی توز",1000,10.0,100,"",""),
        Product("5","پفک لینا",1000,10.0,100,"",""),
        Product("6","چیپس چی توز",1000,10.0,100,"",""),
        Product("7","تاینی",1000,10.0,100,"",""),
        Product("8","کیک",1000,10.0,100,"",""),
    )
    val shareholderList = listOf<Shareholder>(
        Shareholder("","عبدالله ایمانی","",0.5)
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AddPurchaseScreenContent(
            productList = productList,
            shareholderList = shareholderList,

            )
    }
}