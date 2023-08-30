package com.mohyeddin.store_accountent.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.addNumberSeparator
import com.mohyeddin.store_accountent.presentation.common.components.MyText
import com.mohyeddin.store_accountent.presentation.common.components.ToCard
import com.mohyeddin.store_accountent.presentation.common.theme.TOMAN_TXT
import com.mohyeddin.store_accountent.presentation.common.utils.shimmerBrush

@Composable
fun DashboardShimmer() {
    Card() {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .background(shimmerBrush())
            .height(100.dp))
    }
}

@Preview()
@Composable
fun DashboardShimmerPreview() {
    Scaffold() {
        Box(modifier = Modifier.padding(it)){
            DashboardShimmer()
        }
    }
}