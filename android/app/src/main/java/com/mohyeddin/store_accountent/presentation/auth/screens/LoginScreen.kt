package com.mohyeddin.store_accountent.presentation.auth.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.Prefs
import com.mohyeddin.store_accountent.common.withEnglishNumber
import com.mohyeddin.store_accountent.presentation.auth.components.NoticeTextButton
import com.mohyeddin.store_accountent.presentation.auth.stateholders.LoginStateHolder
import com.mohyeddin.store_accountent.presentation.auth.stateholders.rememberViewModelState
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.LoginScreenState
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.LoginViewModel
import com.mohyeddin.store_accountent.presentation.common.components.LoadingButton
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.theme.LOGIN_BTN_TITLE
import com.mohyeddin.store_accountent.presentation.common.theme.PHONE_INPUT_LABEL
import com.mohyeddin.store_accountent.presentation.destinations.SignScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.VerifyScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel = koinViewModel(),
    prefs: Prefs = koinInject()
) {
    val state = loginViewModel.state.collectAsState()
    val isLoading = loginViewModel.isLoading.collectAsState()
    val contentState = rememberViewModelState()

    LaunchedEffect(key1 = state.value){
        when(state.value){
            is LoginScreenState.Success -> {
                prefs.writeToken((state.value as LoginScreenState.Success).data.token)
                navigator.navigate(VerifyScreenDestination(isNewUser = false,phone = contentState.phone.value)){
                    launchSingleTop = true
                }
            }
            is LoginScreenState.Error -> {
                val message = (state.value as LoginScreenState.Error).message
                if (message=="no internet") {
                    contentState.internetDialog.value = true
                }
            }
            else -> {

            }
        }
    }
    MyScaffold(
        viewModel = loginViewModel
    ) {
        LoginScreenContent(
            isLoading = isLoading.value,
            state = contentState,
            onLoginButtonClick = {
                loginViewModel.login(it.withEnglishNumber())
            },
            onNavToSignClick = {
                navigator.navigate(SignScreenDestination){
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    state: LoginStateHolder,
    onLoginButtonClick: (phone: String)->Unit,
    onNavToSignClick: ()->Unit
) {

    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyInputTextField(
            value = state.phone.value ,
            onValueChange = {value->
                state.phone.value = value
                state.validatePhone()
            },
            label = PHONE_INPUT_LABEL,
            isError = !state.isPhoneValid.value,
            supportingText = state.validateMessage.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.size(15.dp))
        LoadingButton(Modifier.fillMaxWidth(),title = LOGIN_BTN_TITLE, isLoading = isLoading, enabled = state.isPhoneValid.value) {
            onLoginButtonClick(state.phone.value)
        }
        Spacer(modifier = Modifier.size(15.dp))
        NoticeTextButton(
            beforeText = "حساب کاربری ندارید؟ ",
            buttonText = "ثبت نام ",
            afterText = "کنید."
        ) {
            onNavToSignClick()
        }
    }
    if (state.internetDialog.value){
        NoInternetDialog {
            state.internetDialog.value = false
        }
    }
}

@Composable
@Preview
fun LoginPreview(){
    var isLoading by remember {
        mutableStateOf(false)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ) {
        Scaffold {
            LoginScreenContent(modifier = Modifier.padding(it),isLoading = isLoading,
                rememberViewModelState(), onLoginButtonClick = {
                isLoading = !isLoading
            }) {
            }
        }
    }
}