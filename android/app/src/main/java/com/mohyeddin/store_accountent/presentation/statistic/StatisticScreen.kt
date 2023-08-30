package com.mohyeddin.store_accountent.presentation.statistic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.common.withPersianNumbers
import com.mohyeddin.store_accountent.domain.shareholder.model.Shareholder
import com.mohyeddin.store_accountent.presentation.common.components.AutoCompleteTextFiled
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.utils.BottomBarNavGraph
import com.mohyeddin.store_accountent.presentation.statistic.viewmodels.StatisticContentStateHolder
import com.mohyeddin.store_accountent.presentation.statistic.viewmodels.StatisticViewModel
import com.mohyeddin.store_accountent.presentation.statistic.viewmodels.rememberStatisticState
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.formatter.ValueFormatter
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph
@Destination
@Composable
fun StatisticScreen(viewModel: StatisticViewModel = koinViewModel()) {


    val purchaseList = viewModel.purchaseList.collectAsState().value.toTypedArray()
    val saleList = viewModel.saleList.collectAsState().value.toTypedArray()
    val profitList = viewModel.profitList.collectAsState().value.toTypedArray()
    val dateList = viewModel.dateList.collectAsState().value
    val shareholders = viewModel.shareholders.collectAsState().value

    val isLoading = viewModel.isLoading.collectAsState()

    val contentState = rememberStatisticState()



    MyScaffold(viewModel) {
        StatisticScreenContent(
            purchaseList = purchaseList, saleList = saleList,
            profitList = profitList,
            dateList = dateList,
            shareholderList = shareholders,
            state = contentState
        ){
            viewModel.fetchStatistics(contentState.selectedTab.value,contentState.selectedShareholder.value)
        }
    }
}

@Composable
fun StatisticScreenContent(
    modifier: Modifier = Modifier,
    purchaseList:Array<Int>,
    saleList: Array<Int>,
    profitList: Array<Int>,
    dateList: List<String>,
    shareholderList: List<Shareholder>,
    state: StatisticContentStateHolder = rememberStatisticState(),
    onTabChange:()->Unit
) {
    state.selectedShareholder.value =  shareholderList.firstOrNull()?.id ?: ""
    Column(
        modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TabRow(
            selectedTabIndex = state.selectedTab.value.ordinal,
            divider = { Divider()}
        ) {
            StatisticContentStateHolder.Tabs.values()
                .filter { it != StatisticContentStateHolder.Tabs.SHAREHOLDERS || shareholderList.isNotEmpty() }
                .forEach{ tab->
                    Tab(
                        selected = state.selectedTab.value == tab,
                        onClick = {
                            state.selectedTab.value = tab
                            onTabChange()
                        },
                    ) {
                        MyText(text = tab.value,
                            modifier = Modifier.padding(10.dp),
                        )
                    }
            }
        }
        if (state.isShareholderTab.value && shareholderList.isNotEmpty()){
            AutoCompleteTextFiled(
                value = shareholderList[0].name ,
                onValueChange = {id,_->
                    state.selectedShareholder.value = id
                },
                suggestionList = shareholderList.associate{
                    Pair(it.id,it.name)
                },
                selectOnly = true
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column (Modifier.padding(10.dp)){
                MyText(text ="آمار فروش", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
                Spacer(modifier = Modifier.size(10.dp))
                StatisticChart(entryList = saleList , valueList = dateList)
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column (Modifier.padding(10.dp)){
                MyText(text ="آمار خرید", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
                Spacer(modifier = Modifier.size(10.dp))
                StatisticChart(entryList = purchaseList , valueList = dateList)
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column (Modifier.padding(10.dp)){
                MyText(text ="آمار سود ماهیانه", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
                Spacer(modifier = Modifier.size(10.dp))
                StatisticChart(entryList = profitList , valueList = dateList)
            }
        }
    }
}

@Composable
fun StatisticChart(entryList: Array<Int>, valueList: List<String>) {
    val entryModel = entryModelOf(*entryList)
    val maxY = if (entryList.isNotEmpty())
        (entryList.maxOf { it } *1.3).toFloat()
    else
        20000f
    Chart(
        chart = columnChart(
            columns = listOf(
                lineComponent(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp),
                )
            ),
            dataLabel = textComponent{
                 this.textSizeSp = 15f
            },
            dataLabelValueFormatter = object : ValueFormatter {
                override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
                    return value.toInt().toString().withPersianNumbers().addNumberSeparator()
                }
            },
            axisValuesOverrider = AxisValuesOverrider.fixed(minY = 0f, maxY = maxY )
        ),
        model = entryModel,

        startAxis = startAxis(
            valueFormatter = {value,_->
                value.toInt().toString().withPersianNumbers().addNumberSeparator()
            },
        ),
        bottomAxis = bottomAxis(
            guideline = null,
            valueFormatter = { value, _ ->
                var bottomValue = ""
                if (valueList.isNotEmpty()) {
                    bottomValue = valueList[value.toInt()].withPersianNumbers()
                }
                bottomValue
            }
        ),
    )
}

@Preview
@Composable
fun MarketStatePreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface {
            StatisticScreenContent(
                purchaseList = arrayOf(1000),
                saleList = emptyArray(),
                profitList = emptyArray(),
                dateList = listOf(
                    "1401-11"
                ),
                shareholderList = listOf(
                    Shareholder("","mohammad","",1.0)
                )
            ){

            }
        }

    }
}