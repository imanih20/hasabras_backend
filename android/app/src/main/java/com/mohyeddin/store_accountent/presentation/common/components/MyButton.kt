package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohyeddin.store_accountent.presentation.common.theme.SIGN_BTN_TITLE

@Composable
fun MyButton(title: String,onclick: ()->Unit, modifier: Modifier = Modifier, enabled : Boolean = true){
    Button(onClick = onclick,modifier = modifier, shape = RoundedCornerShape(10.dp), enabled = enabled) {
        AutoSizeText(text = title,modifier= Modifier.padding(5.dp), style = TextStyle(fontSize = 18.sp))
    }
}