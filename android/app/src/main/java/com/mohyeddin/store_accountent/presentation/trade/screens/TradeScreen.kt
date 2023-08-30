package com.mohyeddin.store_accountent.presentation.trade.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.ToCard
import com.mohyeddin.store_accountent.presentation.common.theme.ADAD_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.KILO_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.TOMAN_TXT
import com.mohyeddin.store_accountent.presentation.common.utils.BottomBarNavGraph
import com.mohyeddin.store_accountent.presentation.dashboard.components.TradListShimmer
import com.mohyeddin.store_accountent.presentation.destinations.FilterTradeListScreenDestination
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.TradesViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import compose.icons.TablerIcons
import compose.icons.tablericons.Filter
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph
@Destination
@Composable
fun TradeScreen(navigator: DestinationsNavigator,viewModel : TradesViewModel = koinViewModel()){
    val tradeList = viewModel.tradeList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    MyScaffold(
        viewModel = viewModel,
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(FilterTradeListScreenDestination) }) {
                IconBox(imageVector = TablerIcons.Filter)
            }
        }
    ){
        if (isLoading.value){
            TradListShimmer()
        }else{
            TradeScreenContent(tradeList = tradeList.value)
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TradeScreenContent(
    modifier: Modifier = Modifier,
    tradeList : List<Trade>,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .padding(10.dp)){
        Column {
            LazyColumn{
                stickyHeader {
                    TradeHeader()
                    Spacer(modifier = Modifier.size(3.dp))
                }
                items(tradeList){trade->
                    TradeItem(trade = trade)
                    Spacer(modifier = Modifier.size(2.dp))
                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .align(alignment = Alignment.BottomCenter)) {
            Row(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                val style = MaterialTheme.typography.titleLarge
                MyText(text = "قیمت کل: ",style = style)
                MyText(
                    tradeList
                        .sumOf { it.totalPrice }
                        .toString()
                        .addNumberSeparator() + " تومان",
                    style = style.copy(color = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }

}


@Composable
fun TradeItem(trade: Trade) {
    val textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
    val type = if (trade.type == "s") "فروش" else "خرید"
    val backColor = if (trade.type == "s") Color.Green else Color.Red
    val unit = if (trade.unit == "n") ADAD_TXT else KILO_TXT
    val quantity = if(trade.unit == "n") trade.quantity.toInt() else trade.quantity
    ToCard(modifier = Modifier, firstBackground = backColor, secondBackground = MaterialTheme.colorScheme.primaryContainer) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            MyText(type, style = textStyle, modifier = Modifier.weight(1f))
            MyText(trade.productName,style = textStyle, modifier = Modifier.weight(1f))
            MyText("$quantity $unit",style = textStyle, modifier = Modifier.weight(1f))
            MyText(trade.totalPrice.toString().addNumberSeparator()+"\n"+ TOMAN_TXT,style = textStyle, modifier = Modifier.weight(1f))
            MyText(trade.date,style = textStyle, modifier = Modifier.weight(1f))
        }
    }
}
@Composable
fun TradeHeader(){
    val textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(start = 5.dp),
            ) {
            MyText("نوع", style = textStyle, modifier = Modifier.weight(1f))
            MyText("اسم کالا",style = textStyle, modifier = Modifier.weight(1f))
            MyText("مقدار",style = textStyle, modifier = Modifier.weight(1f))
            MyText("قیمت کل",style = textStyle, modifier = Modifier.weight(1f))
            MyText("تاریخ",style = textStyle, modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradePreview() {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TradeScreenContent(tradeList = listOf(
            Trade("","رب",10.0,10000,"p","n","1402-12-03")
        ))
    }
}