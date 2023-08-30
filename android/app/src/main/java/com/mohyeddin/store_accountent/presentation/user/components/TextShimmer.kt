package com.mohyeddin.store_accountent.presentation.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.presentation.common.utils.shimmerBrush

@Composable
fun TextShimmer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier
        .height(30.dp)
        .background(shape = RoundedCornerShape(5.dp), brush = shimmerBrush()))
}