package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mohyeddin.store_accountent.presentation.common.utils.BaseViewModel

@Composable
fun MyScaffold(
    viewModel : BaseViewModel,
    topBar: @Composable (()->Unit) = {},
    bottomBar: @Composable (()->Unit) = {},
    floatingActionButton: @Composable (()->Unit) = {},
    content: @Composable (()->Unit)
) {
    val showInternetDialog = viewModel.showInternetDialog.collectAsState()
    val snackBarLevel = viewModel.snackBarLevel.collectAsState()
    val snackBarMsg = viewModel.snackBarMessage.collectAsState()
    val snackId = viewModel.snackId.collectAsState()
    val snackBarState = remember{
        SnackbarHostState()
    }
    LaunchedEffect(key1 = snackId.value){
        if (snackId.value != 0.0){
            snackBarState.showSnackbar(snackBarMsg.value, duration = SnackbarDuration.Short)
        }
    }

    Scaffold(
        topBar = topBar,
        snackbarHost = {
            SnackbarHost(snackBarState) {
                MySnackBar(
                    data = it,
                    level = snackBarLevel.value)

            }
        },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton
    ) {
        Box(Modifier.padding(it)) {
            content()
        }
        if (showInternetDialog.value){
            NoInternetDialog {
                viewModel.setShowInternetDialog(false)
            }
        }
    }
}