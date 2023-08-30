package com.mohyeddin.store_accountent.presentation.statistic

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.domain.statistic.models.MonthStatistic
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.MyScaffold
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.theme.TOMAN_TXT
import com.mohyeddin.store_accountent.presentation.statistic.viewmodels.ShareholderStatisticViewModel
import com.ramcosta.composedestinations.annotation.Destination
import compose.icons.TablerIcons
import compose.icons.tablericons.Check
import compose.icons.tablericons.X
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Destination
@Composable
fun ShareholderStatisticScreen(
    shareholderId: String,
    viewModel: ShareholderStatisticViewModel = koinViewModel{
        parametersOf(shareholderId)
    }
) {
    val statisticList =  viewModel.statistics.collectAsState()
    MyScaffold(viewModel = viewModel) {
        ShareholderStatisticContent(statisticList = statisticList.value, onUpdate = {id,isPaid->
            viewModel.update(id,isPaid)
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShareholderStatisticContent(
    modifier: Modifier = Modifier,
    statisticList: List<MonthStatistic>,
    onUpdate:(String,Boolean)->Unit
){
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(10.dp)){
        stickyHeader {
            ListHeader()
        }
        items(statisticList){ statistic ->
            ShareholderStatisticItem(monthStatistic = statistic){
                onUpdate(statistic.id,it)
            }
            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}

@Composable
fun ListHeader(modifier: Modifier = Modifier) {
    val style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)

    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondary
    ), modifier = modifier) {
        Row (
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            MyText(text = "ماه",modifier = Modifier.weight(1f),style = style)
            MyText(text = "مقدار سود",modifier = Modifier.weight(1f),style = style)
            MyText(text = "وضعیت واریز",modifier = Modifier.weight(1f),style = style)
        }
    }
}

@Composable
fun ShareholderStatisticItem(monthStatistic: MonthStatistic,updateIsPaid:(Boolean)->Unit) {
    var isChecked by remember{
        mutableStateOf(monthStatistic.isPaid)
    }
    val style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
    Card{
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            MyText(text = "${monthStatistic.year}-${monthStatistic.month}",style = style, modifier = Modifier.weight(
                1f
            ))
            MyText(
                text = monthStatistic.profit.toString().addNumberSeparator() + " " + TOMAN_TXT,
                style = style,
                modifier = Modifier.weight(1f)
            )
            IconToggleButton(checked = isChecked, onCheckedChange = {
                isChecked = !isChecked
                updateIsPaid(isChecked)
            }, modifier = Modifier.weight(1f)) {
                IconBox(
                    imageVector = if (isChecked) TablerIcons.Check else TablerIcons.X,
                    tint = if (isChecked) Color.Green else Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShareStatePreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ShareholderStatisticContent(
            statisticList = listOf(
                MonthStatistic("","",30000,30000,200000000,1402,5,true)
            )
        ){_,_->

        }

    }
}