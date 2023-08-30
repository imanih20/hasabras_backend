package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MySnackBar(
    data: SnackbarData,
    level: SnackBarLevel
) {
    Snackbar(
        snackbarData = data,
        containerColor = level.backColor,
        contentColor = level.textColor
    )
}



enum class SnackBarLevel(val backColor: Color ,val textColor: Color = Color.White){
    Info(Color.DarkGray),Error(Color.Red),Success(Color.Green, Color.Black)
}
