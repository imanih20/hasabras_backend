package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun ToCard(modifier: Modifier = Modifier,firstBackground: Color,secondBackground: Color,content: @Composable (()->Unit)) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = firstBackground
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            colors = CardDefaults.cardColors(
                containerColor = secondBackground
            )
        ) {
            content()
        }
    }
}