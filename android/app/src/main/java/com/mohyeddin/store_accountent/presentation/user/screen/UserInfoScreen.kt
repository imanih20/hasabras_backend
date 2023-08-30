package com.mohyeddin.store_accountent.presentation.user.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.domain.market.model.Market
import com.mohyeddin.store_accountent.domain.user.model.User
import com.mohyeddin.store_accountent.presentation.common.components.MyDialog
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.NoInternetDialog
import com.mohyeddin.store_accountent.presentation.common.utils.BottomBarNavGraph
import com.mohyeddin.store_accountent.presentation.common.utils.shimmerBrush
import com.mohyeddin.store_accountent.presentation.user.components.TextShimmer
import com.mohyeddin.store_accountent.presentation.user.viewmodel.UserViewModel
import com.mohyeddin.store_accountent.presentation.user.viewmodel.rememberEditDialogState
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph
@Destination
@Composable
fun UserInfoScreen(viewModel: UserViewModel = koinViewModel()) {
    val user = viewModel.user.collectAsState().value
    val market = viewModel.market.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState()

    MyScaffold(viewModel = viewModel) {
        UserInfoScreenContent(
            userInfo = user,
            marketInfo = market,
            isLoading = isLoading.value,
            onUserUpdate = {
                viewModel.updateUser(it)
            },
            onMarketUpdate = {
                viewModel.updateMarketInfo(it)
            }
        )
    }
}

@Composable
fun UserInfoScreenContent(
    modifier: Modifier = Modifier,
    userInfo: User?,
    marketInfo: Market?,
    isLoading: Boolean,
    onUserUpdate: (String) -> Unit,
    onMarketUpdate: (String) -> Unit
) {
    var dialogState by remember {
        mutableStateOf(false)
    }
    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp),
        ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
                ShowText(title = "نام:", text = userInfo?.name ?: "", isLoading = isLoading )
                Spacer(modifier = Modifier.size(10.dp))
                ShowText(title = "شماره تلفن:", text = userInfo?.phone ?: "", isLoading = isLoading)
                Spacer(modifier = Modifier.size(10.dp))
                ShowText(title = "نام فروشگاه:", text = marketInfo?.name ?: "", isLoading = isLoading )
                IconButton(onClick = { dialogState = true }, modifier = Modifier.align(Alignment.End)) {
                    Icon(Icons.Default.Edit, contentDescription = "")
                }
            }
        }
    }
    if (dialogState && userInfo!==null && marketInfo!=null){
        EditDialog(
            onDismissRequest = { dialogState = false },
            name = userInfo.name,
            marketName = marketInfo.name ,
            onUserUpdate = onUserUpdate,
            onMarketUpdate = onMarketUpdate
        )
    }
}

@Composable
fun ShowText(title: String,text: String, isLoading: Boolean) {
    val style = MaterialTheme.typography.headlineSmall
    Row(verticalAlignment = Alignment.CenterVertically) {
        MyText(text = title, style = style)
        Spacer(modifier = Modifier.size(5.dp))
        if (isLoading){
            TextShimmer(Modifier.fillMaxWidth(0.4f))
        }else{
            MyText(
                text,
                style = style.copy(color = MaterialTheme.colorScheme.primary)
            )
        }
    }
}
@Composable
fun EditDialog(
    onDismissRequest : ()->Unit,
    name: String,
    marketName: String,
    onUserUpdate: (String)->Unit,
    onMarketUpdate: (String) -> Unit
) {
    val state = rememberEditDialogState(name = name, marketName = marketName
    )
    MyDialog(
        onDismissRequest = onDismissRequest,
        negativeBtnText = "لغو",
        positiveBtnTxt = "ثبت",
        onPositiveBtnClick = {
            if(state.validateUser()){
                onUserUpdate(state.userName.value)
            }
            if(state.validateMarket()){
                onMarketUpdate(state.market.value)
            }
        }
    ) {
        Column {
            MyInputTextField(
                value = state.userName.value,
                onValueChange = {
                    state.isUserInfoChange.value = it.trim() != name
                    state.userName.value = it
                },
                label = "نام",
            )
            Spacer(modifier = Modifier.size(10.dp))
            MyInputTextField(
                value = state.market.value,
                onValueChange = {
                    state.isMarketInfoChange.value = it.trim() != marketName
                    state.market.value = it
                },
                label = "اسم فروشگاه"
            )
        }
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    val userInfo = User("","محی الدین","09374691756")
    val marketInfo = Market("","فروشگاه مهر",userInfo.id)
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        UserInfoScreenContent(userInfo = userInfo, marketInfo = marketInfo, isLoading = true, onUserUpdate = {}, onMarketUpdate = {})
    }
}