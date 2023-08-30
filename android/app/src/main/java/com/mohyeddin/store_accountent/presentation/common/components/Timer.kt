package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TimerButton(
    modifier: Modifier = Modifier,
    currentTime: Int,
    title: String = "",
    onClick:()->Unit) {

    Button(onClick = onClick, enabled = currentTime == 0, shape = RoundedCornerShape(10.dp), modifier=modifier) {
        MyText(
            text = if(currentTime == 0) title else "${currentTime / 60}:${currentTime % 60}",
            modifier= Modifier.padding(5.dp)
        )
    }
}