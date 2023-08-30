package com.mohyeddin.store_accountent.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.domain.trade.model.Trade
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.ToCard
import com.mohyeddin.store_accountent.presentation.common.theme.TODAY_SALE_TEXT
import com.mohyeddin.store_accountent.presentation.common.theme.TOMAN_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.TOTAL_PROFIT_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.TOTAL_PURCHASE_TXT
import com.mohyeddin.store_accountent.presentation.common.theme.TOTAL_SALE_TXT
import com.mohyeddin.store_accountent.presentation.common.utils.BottomBarNavGraph
import com.mohyeddin.store_accountent.presentation.dashboard.components.DashboardShimmer
import com.mohyeddin.store_accountent.presentation.dashboard.components.TradListShimmer
import com.mohyeddin.store_accountent.presentation.dashboard.viewmodel.DashboardViewModel
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun DashboardScreen(viewModel: DashboardViewModel = koinViewModel()) {
    val purchase = viewModel.purchase.collectAsState()
    val sale = viewModel.sale.collectAsState()
    val profit = viewModel.profit.collectAsState()
    val lastPurchases = viewModel.lastPurchases.collectAsState()
    val lastSales = viewModel.lastSales.collectAsState()
    val lastProfits = viewModel.lastProfits.collectAsState()
    val tradeList = viewModel.trades.collectAsState()

    val isTradeLoading = viewModel.isTradeLoading.collectAsState()
    val isStateLoading = viewModel.isLoading.collectAsState()


    MyScaffold(viewModel = viewModel){
        DashboardScreenContent(
            tradeList = tradeList.value,
            totalPurchase = purchase.value,
            totalSale = sale.value,
            totalProfit = profit.value,
            lastPurchases = lastPurchases.value,
            lastSales = lastSales.value,
            lastProfits = lastProfits.value,
            isStatLoading = isStateLoading.value,
            isTradeLoading = isTradeLoading.value
        )
    }

}

@Composable
fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    isTradeLoading: Boolean,
    isStatLoading: Boolean,
    tradeList: List<Trade>,
    totalPurchase: Int,
    totalSale: Int,
    totalProfit: Int,
    lastPurchases: Array<Int>,
    lastSales: Array<Int>,
    lastProfits: Array<Int>
) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (isStatLoading){
                LazyColumn{
                    items(3){
                        DashboardShimmer()
                    }
                }
            }else{
                DashboardCard(
                    title = TOTAL_PURCHASE_TXT,
                    value = "$totalPurchase",
                    lastPurchases
                )
                Spacer(modifier = Modifier.size(3.dp))
                DashboardCard(
                    title = TOTAL_SALE_TXT,
                    value = "$totalSale",
                    lastSales
                )
                Spacer(modifier = Modifier.size(3.dp))
                DashboardCard(
                    title = TOTAL_PROFIT_TXT,
                    value = "$totalProfit",
                    lastProfits
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            MyText(
                TODAY_SALE_TEXT,
                style = MaterialTheme.typography.headlineSmall
            )
            if (isTradeLoading){
                TradListShimmer()
            }else{
                LazyColumn {
                    items(tradeList){trade->
                        TradeItem(trade = trade)
                    }
                }
            }
        }

}
@Composable
fun TradeItem(trade: Trade) {
    val type = if (trade.type == "s") "فروش" else "خرید"
    val backColor = if (trade.type == "s") Color.Green else Color.Red
    ToCard(modifier = Modifier.padding(start = 10.dp), firstBackground = backColor, secondBackground = MaterialTheme.colorScheme.primaryContainer) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            MyText(type, style = MaterialTheme.typography.bodyLarge)
            MyText(trade.productName,style = MaterialTheme.typography.bodyLarge)
            MyText(trade.totalPrice.toString().addNumberSeparator()+" "+TOMAN_TXT,style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String,
    lastPrices : Array<Int> = emptyArray()
) {
    ToCard(firstBackground = MaterialTheme.colorScheme.primary, secondBackground = MaterialTheme.colorScheme.primaryContainer) {
        val chartModelEntry = entryModelOf(*lastPrices)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (lastPrices.isEmpty())Arrangement.Start else Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                MyText(
                    text = title,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MyText(
                        text = value.addNumberSeparator(),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    MyText(
                        text = TOMAN_TXT,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            if(lastPrices.isNotEmpty()) Chart(chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.fixed(minY = 0f, maxY = lastPrices.maxOf { it }.toFloat())
            ), model = chartModelEntry, Modifier.size(80.dp))
        }
    }
}

@Preview
@Composable
fun DashBoardPreview() {
    val tradeList = listOf(
        Trade("","بوم بوم",5.0,100000,"s","n","")
    )
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        DashboardScreenContent(
            tradeList = tradeList,
            totalPurchase = 10000000 ,
            totalSale = 20000000,
            totalProfit = 200000,
            lastPurchases = arrayOf(100000,222222,111000),
            lastSales = arrayOf(100000,222222,111000),
            lastProfits = arrayOf(100000,222222,111000),
            isTradeLoading = false,
            isStatLoading = false
        )
    }
}