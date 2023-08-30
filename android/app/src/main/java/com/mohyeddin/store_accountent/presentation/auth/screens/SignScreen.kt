package com.mohyeddin.store_accountent.presentation.auth.screens

import android.util.Log
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
import com.mohyeddin.store_accountent.presentation.auth.stateholders.SignStateHolder
import com.mohyeddin.store_accountent.presentation.auth.stateholders.rememberSignState
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.SignScreenState
import com.mohyeddin.store_accountent.presentation.common.components.LoadingButton
import com.mohyeddin.store_accountent.presentation.auth.viewmodels.SignViewModel
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.theme.NAME_INPUT_LABEL
import com.mohyeddin.store_accountent.presentation.common.theme.PHONE_INPUT_LABEL
import com.mohyeddin.store_accountent.presentation.common.theme.SIGN_BTN_TITLE
import com.mohyeddin.store_accountent.presentation.destinations.LoginScreenDestination
import com.mohyeddin.store_accountent.presentation.destinations.VerifyScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Destination
@Composable
fun SignScreen(navigator: DestinationsNavigator,viewModel: SignViewModel = koinViewModel(),prefs: Prefs = koinInject()){
    val state = viewModel.state.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val contentState = rememberSignState()

    LaunchedEffect(key1 = state.value){
        Log.i("login_state",state.value.toString())
        when(state.value){
            is SignScreenState.Error -> {
                when((state.value as SignScreenState.Error).code){

                    403->{
                        contentState.isPhoneValid.value = false
                        contentState.phoneError.value = "شماره تلفن قبلا ثبت نام کرده است لطفا وارد شوید"
                    }
                }
            }
            is SignScreenState.Success -> {
                prefs.writeToken((state.value as SignScreenState.Success).data.token)
                navigator.navigate(VerifyScreenDestination(isNewUser = true,phone = contentState.phone.value)){
                    launchSingleTop = true
                }
            }
            else -> {}
        }
    }
    MyScaffold(
        viewModel
    ) {
        SignScreenContent(
            isLoading = isLoading.value,
            state = contentState,
            onNavToLoginClick = {navigator.navigate(LoginScreenDestination)}
        ){
            viewModel.sign(contentState.name.value,contentState.phone.value.withEnglishNumber())
        }
    }
}

@Composable
fun SignScreenContent(
    modifier: Modifier = Modifier,
    isLoading : Boolean = false,
    state: SignStateHolder,
    onNavToLoginClick: ()->Unit,
    onSignBtnClick: ()->Unit
) {

    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            MyInputTextField(
                value = state.name.value,
                onValueChange = {value->
                    state.name.value = value
                    state.validateName()
                },
                modifier=Modifier.fillMaxWidth(),
                label = NAME_INPUT_LABEL,
                isError = !state.isNameValid.value,
                supportingText = state.nameError.value
            )
            MyInputTextField(
                value = state.phone.value,
                onValueChange = {value->
                    state.phone.value = value
                    state.validatePhone()
                },
                modifier = Modifier.fillMaxWidth(),
                label= PHONE_INPUT_LABEL,
                isError = !state.isPhoneValid.value,
                supportingText = state.phoneError.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        LoadingButton(
            title = SIGN_BTN_TITLE,
            modifier = Modifier.fillMaxWidth(),
            isLoading = isLoading,
            enabled = (state.isNameValid.value && state.isPhoneValid.value)
        ){
            onSignBtnClick()
        }
        Spacer(modifier = Modifier.size(15.dp))
        NoticeTextButton(
            beforeText = "حساب کاربری دارید؟ " ,
            buttonText = "وارد ",
            afterText ="شوید"
        ) {
            onNavToLoginClick()
        }
    }
}

@Composable
@Preview
fun Preview(){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold {
            SignScreenContent(onNavToLoginClick = { /*TODO*/ },state = rememberSignState(), modifier = Modifier.padding(it)){}
        }
    }
}