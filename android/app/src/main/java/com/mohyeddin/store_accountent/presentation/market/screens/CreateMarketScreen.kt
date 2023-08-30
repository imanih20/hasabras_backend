package com.mohyeddin.store_accountent.presentation.market.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.Prefs
import com.mohyeddin.store_accountent.presentation.common.components.LoadingButton
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.theme.MARKET_NAME_INPUT_LABEL
import com.mohyeddin.store_accountent.presentation.common.theme.SAVE_BTN_TITLE
import com.mohyeddin.store_accountent.presentation.destinations.AddShareholderScreenDestination
import com.mohyeddin.store_accountent.presentation.market.viewmodels.CreateMarketState
import com.mohyeddin.store_accountent.presentation.market.viewmodels.CreateMarketViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Destination
@Composable
fun CreateMarketScreen(navigator: DestinationsNavigator,viewModel: CreateMarketViewModel = koinViewModel(),prefs: Prefs = koinInject()) {
    val isLoading = viewModel.isLoading.collectAsState()

    MyScaffold(viewModel = viewModel) {
        CreateMarketScreenContent(
            isLoading = isLoading.value
        ){
            viewModel.insert(it){
                prefs.writeIsSigned(true)
                navigator.navigate(AddShareholderScreenDestination(isSigning = true))
            }
        }
    }
}

@Composable
fun CreateMarketScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onSaveBtnClick: (String)->Unit
) {
    var marketName by remember {
        mutableStateOf("")
    }
    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MyInputTextField(
            value = marketName,
            onValueChange = {value->
                marketName = value
            },
            label = MARKET_NAME_INPUT_LABEL
        )
        Spacer(modifier = Modifier.size(15.dp))
        LoadingButton(title = SAVE_BTN_TITLE, isLoading = isLoading, modifier = Modifier.fillMaxWidth()){
            onSaveBtnClick(marketName)
        }
    }
}

@Preview
@Composable
fun CreateMarketPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        CreateMarketScreenContent{}
    }
}