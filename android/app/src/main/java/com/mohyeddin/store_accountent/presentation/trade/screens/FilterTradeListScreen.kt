package com.mohyeddin.store_accountent.presentation.trade.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohyeddin.datepicker.date.DatePickerDialog
import com.mohyeddin.store_accountent.presentation.common.components.IconBox
import com.mohyeddin.store_accountent.presentation.common.components.MyButton
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.trade.stateholders.FilterScreenStateHolder
import com.mohyeddin.store_accountent.presentation.trade.stateholders.rememberFilterScreenState
import com.mohyeddin.store_accountent.presentation.trade.utils.TypeOptions
import com.mohyeddin.store_accountent.presentation.trade.viewmodel.FilterViewModel
import com.ramcosta.composedestinations.annotation.Destination
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun FilterTradeListScreen(viewModel: FilterViewModel = koinViewModel()) {
    val contentState = rememberFilterScreenState(date = viewModel.getDate(), type = viewModel.getType())
    Scaffold(
        bottomBar = {
            MyButton(
                title = "ذخیره",
                onclick = {
                    viewModel.saveDate(contentState.date)
                    viewModel.saveType(contentState.type)
                },
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            )
        }
    ) {
        FilterContent(Modifier.padding(it),state = contentState)
    }
}

@Composable
fun FilterContent(
    modifier: Modifier = Modifier,
    state: FilterScreenStateHolder,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(10.dp)) {
        MyText(text = "تاریخ:", fontSize = 16.sp)
        Column(
        ) {
            RadioTextButton(title = "تمام روزها", selected = state.isDateCheck) {
                state.toggleCheck(true)
            }
            RadioTextButton(title = "انتخاب تاریخ", selected = !state.isDateCheck) {
                state.toggleCheck(false)
            }
        }
        AnimatedVisibility(visible = !state.isDateCheck) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                MyText(text = "با کلیک بر روی آیکون تقویم تاریخ مورد نظر خود را انتخاب کنید", fontSize = 12.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(5.dp)
                        )
                ){
                    Row(
                        Modifier.padding(start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        MyText(text = state.date)
                        Spacer(modifier = Modifier.size(5.dp))
                        IconButton(onClick = { state.datePickerState = true }) {
                            IconBox(imageVector = TablerIcons.Calendar, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        MyText(text = "نوع:", fontSize = 16.sp)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TypeOptions.values().forEach {
                RadioTextButton(title = it.title, selected = state.type == it.value) {
                    state.type = it.value
                }
            }
        }
    }
    if (state.datePickerState){
        DatePickerDialog(onDismissRequest = { state.datePickerState = false }, onSubmitClicked = {
            state.date = it
        })
    }
}

@Composable
fun RadioTextButton(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: ()->Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        MyText(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun FilterPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        FilterContent(state =rememberFilterScreenState(date = "", type = ""))
    }
}