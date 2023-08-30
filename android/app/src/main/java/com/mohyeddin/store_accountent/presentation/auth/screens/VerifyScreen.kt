package com.mohyeddin.store_accountent.presentation.auth.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.Prefs
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.presentation.common.components.LoadingButton
import com.mohyeddin.store_accountent.presentation.auth.stateholders.VerifyStateHolder
import com.mohyeddin.store_accountent.presentation.auth.stateholders.rememberVerifyState
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.VerifyScreenState
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.VerifyViewModel
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.components.SnackBarLevel
import com.mohyeddin.store_accountent.presentation.common.components.TimerButton
import com.mohyeddin.store_accountent.presentation.common.theme.VERIFY_BTN_TITLE
import com.mohyeddin.store_accountent.presentation.destinations.CreateMarketScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Destination
@Composable
fun VerifyScreen(isNewUser: Boolean,phone: String,navigator: DestinationsNavigator, viewModel: VerifyViewModel = koinViewModel(),prefs: Prefs = koinInject()) {
    val state = viewModel.state.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val contentState = rememberVerifyState(rememberCoroutineScope())
    BackHandler {
        navigator.popBackStack()
    }
    LaunchedEffect(key1 = state.value){
        when(state.value){
            is VerifyScreenState.Error -> {
                when((state.value as VerifyScreenState.Error).code){
                    404 -> {
                        contentState.setError("کد وارد شده اشتباه است.")
                    }
                }
                viewModel.showSnackBar(SnackBarLevel.Error,
                    (state.value as VerifyScreenState.Error).message
                )
            }
            is VerifyScreenState.Success -> {
                if (isNewUser){
                    navigator.navigate(CreateMarketScreenDestination){
                        launchSingleTop = true
                    }
                }else {
                    prefs.writeIsSigned(true)
                    navigator.navigate(MainScreenDestination){
                        launchSingleTop = true
                    }
                }
            }
            else -> {}
        }
    }
    MyScaffold(
        viewModel
    ) {
        VerifyScreenContent(
            state=contentState,
            phone = phone,
            isLoading = isLoading.value,
            onNavBack = {
                navigator.popBackStack()
            }
        ){
            contentState.hidError()
            viewModel.verify(contentState.code.value.withEnglishNumber())
        }
    }
}

@Composable
fun VerifyScreenContent(
    state: VerifyStateHolder,
    modifier : Modifier = Modifier,
    phone : String = "",
    isLoading: Boolean = false,
    onNavBack:()->Unit,
    onVerifyBtnClick: ()->Unit
) {

    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
        ) {
        MyInputTextField(
            value = state.code.value,
            onValueChange = {
                state.code.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = "کد فعالسازی",
            isError = state.isCodeError.value,
            supportingText = state.codeError.value
        )
        LoadingButton(Modifier.fillMaxWidth(),title = VERIFY_BTN_TITLE, isLoading = isLoading) {
            onVerifyBtnClick()
        }
        Spacer(modifier = Modifier.size(5.dp))
        TimerButton(currentTime = state.currentTimerTime.value, title = "ارسال مجدد", modifier = Modifier.fillMaxWidth()) {
            onNavBack()
        }
        Spacer(modifier = Modifier.size(15.dp))
        MyText(
            text = "پیامکی حاوی کد فعالسازی به شماره $phone ارسال شده است لطفا آن را وارد کنید.",
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun VerifyPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        VerifyScreenContent(rememberVerifyState(rememberCoroutineScope()), onNavBack = {}) {

        }
    }
}