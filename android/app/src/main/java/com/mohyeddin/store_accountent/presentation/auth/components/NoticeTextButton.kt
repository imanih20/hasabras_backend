package com.mohyeddin.store_accountent.presentation.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.theme.IS_SIGN_NOTICE
import com.mohyeddin.store_accountent.presentation.common.theme.SIGN_BTN_TITLE

@Composable
fun NoticeTextButton(
    beforeText: String,
    buttonText: String,
    afterText: String,
    onButtonClick: ()->Unit
) {
    Row {
        MyText(text = beforeText, fontSize = 18.sp)
        MyText(
            text = buttonText,
            color = MaterialTheme.colorScheme.primary,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.clickable {
                onButtonClick()
            }
        )
        MyText(text = afterText, fontSize = 18.sp)
    }
}