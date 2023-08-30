package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.theme.LOGIN_BTN_TITLE

@Composable
fun LoadingButton(modifier: Modifier = Modifier,title: String,isLoading : Boolean = false,enabled: Boolean = true, onClick: ()->Unit) {
    val background = if (isLoading) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primary
    Button(
        onClick =  onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = background
        ),
        enabled = !isLoading && enabled
    ) {
        if (isLoading) CircularProgressIndicator()
        else MyText(text = title,modifier= Modifier.padding(5.dp), style = TextStyle(fontSize = 18.sp))
    }
}