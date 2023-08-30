package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun AutoCompleteTextFiled(
    value: String,
    onValueChange: (id: String,text: String)->Unit,
    suggestionList : Map<String,String>,
    modifier: Modifier = Modifier,
    label: String = "",
    selectOnly : Boolean  = false,
    isError: Boolean = false,
    supportingText: String = ""
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var textFieldSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    var text by remember {
        mutableStateOf(suggestionList[value]?:"")
    }
    var id by remember {
        mutableStateOf("")
    }
    Column(modifier) {
        MyInputTextField(
            value = text,
            onValueChange = { value ->
                text = value
                expanded = true
                id = suggestionList.filter { text == it.value }.keys.firstOrNull() ?: ""
                onValueChange(id,text)
            },
            modifier = Modifier
                .onGloballyPositioned { textFieldSize = it.size }
                .onFocusEvent { expanded = it.isFocused }
            ,
            label = label,
            trailingIcon = {
                Icon(Icons.Rounded.ArrowDropDown, contentDescription = "arrow")
            },
            readOnly = selectOnly,
            isError = isError,
            supportingText = supportingText
        )
        if (suggestionList.isNotEmpty()) AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .width(textFieldSize.width.dp),
                elevation = CardDefaults.cardElevation(15.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp),
                ) {

                    if (suggestionList.isNotEmpty() && !selectOnly) {
                        items(
                            suggestionList.values.filter {
                                it.lowercase()
                                    .contains(value.lowercase()) || it.lowercase()
                                    .contains("others")
                            }.sorted()
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        text = it
                                        expanded = false
                                        id = suggestionList.filter { it.value == text }.keys.firstOrNull() ?: ""
                                        onValueChange(id,text)
                                    }
                            ) {
                                MyText(text = it)
                            }
                        }
                    } else {
                        items(
                            suggestionList.values.sorted()
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        text = it
                                        expanded = false
                                        id = suggestionList.filter { it.value == text }.keys.firstOrNull()?:""
                                        onValueChange(id,text)
                                    }
                            ) {
                                MyText(text = it)
                            }
                        }
                    }

                }

            }
        }

    }

}

