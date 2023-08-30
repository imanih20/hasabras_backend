package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.mohyeddin.store_accountent.common.withPersianNumbers
import com.mohyeddin.store_accountent.presentation.common.theme.Vazir


@Composable
fun MyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    textAlign : TextAlign? = null,
){
    Text(
        text.withPersianNumbers(),
        modifier,
        color,
        fontSize,
        style = style.copy(fontFamily = Vazir),
        textAlign = textAlign
    )
}