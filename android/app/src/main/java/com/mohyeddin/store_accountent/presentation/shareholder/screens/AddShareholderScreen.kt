package com.mohyeddin.store_accountent.presentation.shareholder.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.presentation.common.components.MyButton
import com.mohyeddin.store_accountent.presentation.common.components.MyDialog
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.theme.SAVE_BTN_TITLE
import com.mohyeddin.store_accountent.presentation.destinations.MainScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.ShareholderStatisticScreenDestination
import com.mohyeddin.store_accountent.presentation.shareholder.components.ListItemShimmer
import com.mohyeddin.store_accountent.presentation.shareholder.viewmodels.ShareholderViewModel
import com.mohyeddin.store_accountent.presentation.statistic.ShareholderStatisticScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun AddShareholderScreen(
    navigator: DestinationsNavigator,
    viewModel: ShareholderViewModel = koinViewModel(),
    isSigning: Boolean = false
) {
    val shareholders = viewModel.shareholders.collectAsState()

    val isLoading = viewModel.isLoading.collectAsState()

    MyScaffold(viewModel) {
        ShareholderScreenContent(
            shareholderList = shareholders.value,
            isSigning = isSigning,
            isLoading = isLoading.value,
            onSkipBtnClick = {
                navigator.navigate(MainScreenDestination)
            },
            onItemclick = {
                navigator.navigate(ShareholderStatisticScreenDestination(shareholderId = it))
            }
        ){name,phone,share->
            viewModel.addShareholder(name, phone, share)
        }
    }
}

@Composable
fun ShareholderScreenContent(
    modifier: Modifier = Modifier,
    shareholderList: List<Shareholder>,
    isSigning: Boolean = false,
    isLoading: Boolean = false,
    onSkipBtnClick: ()->Unit,
    onItemclick: (String) ->Unit,
    onSaveAction: (name: String,phone: String,share: Double)->Unit
) {
    var dialogState by remember {
        mutableStateOf(false)
    }
    Box(
        modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        if (isLoading){
            LazyColumn{
                items(5){
                    ListItemShimmer()
                    Divider()
                }
            }
        }
        if (shareholderList.isEmpty() && !isLoading){
            Box(Modifier.padding(10.dp)){
                MyText(
                    text = "در صورتی که فروشگاه شما دارای سهامدار است می توانید در این قسمت مشخصات آنها را وارد کنید.",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }else{
            LazyColumn {
                items(shareholderList) {sh->
                    Card(
                        Modifier.clickable { onItemclick(sh.id) },
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        ListItem(
                            headlineContent = {
                                MyText(text = sh.name)
                            },
                            supportingContent =
                            {
                                MyText(sh.phone)
                            },
                            trailingContent = {
                                MyText("${sh.share*100}%")
                            },
                            leadingContent = {
                                Box(modifier = Modifier
                                    .border(
                                        1.dp,
                                        Color.Black, shape = CircleShape
                                    )
                                    .padding(5.dp)){
                                    Icon(
                                        Icons.Filled.Person,
                                        contentDescription ="",
                                        Modifier
                                            .size(40.dp),
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            FloatingActionButton(onClick = { dialogState = true }, modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
            if (isSigning){
                Spacer(modifier = Modifier.size(10.dp))
                MyButton(title = "رد شدن", onclick = { onSkipBtnClick() },Modifier.fillMaxWidth())
            }
        }
    }
    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var share by remember {
        mutableStateOf("")
    }
    if (dialogState)
        MyDialog(
            onDismissRequest = { dialogState = false },
            onPositiveBtnClick ={
                onSaveAction(name,phone.withEnglishNumber(),share.withEnglishNumber().toDouble()/100)
            },
            positiveBtnTxt = SAVE_BTN_TITLE,
            negativeBtnText = "لغو"
        ) {
            Column(Modifier.padding(15.dp)) {
                MyInputTextField(
                    value = name,
                    onValueChange = {name = it},
                    label = "نام"
                )
                Spacer(modifier = Modifier.size(10.dp))
                MyInputTextField(
                    value = phone,
                    onValueChange = {phone = it},
                    label = "شماره تلفن"
                )
                Spacer(modifier = Modifier.size(10.dp))
                MyInputTextField(
                    value = share,
                    onValueChange = {share = it},
                    label = "میزان سهم به درصد",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

        }
}
@Preview
@Composable
fun AddShareholderPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ShareholderScreenContent(shareholderList = emptyList(), onSkipBtnClick = {}, onItemclick = {}){
            name,phone,share->
        }
    }
}