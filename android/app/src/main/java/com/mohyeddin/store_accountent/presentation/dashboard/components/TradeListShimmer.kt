package com.mohyeddin.store_accountent.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.presentation.common.utils.shimmerBrush

@Composable
fun TradListShimmer() {
    LazyColumn{
        items(10){
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush = shimmerBrush()))
            Spacer(modifier = Modifier.size(3.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListShimmerPreview() {
    TradListShimmer()
}