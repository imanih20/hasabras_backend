package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ShowStatsText(
    modifier: Modifier  = Modifier,
    label: String,
    value: String,
) {
    val textStyle = MaterialTheme.typography.bodyLarge
    Row(modifier,verticalAlignment = Alignment.CenterVertically) {
        MyText(text = label,style = textStyle)
        MyText(text = value, style = textStyle.copy(color = MaterialTheme.colorScheme.secondary))
    }
}