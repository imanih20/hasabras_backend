package com.mohyeddin.store_accountent.presentation.shareholder.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.presentation.user.components.TextShimmer

@Composable
fun ListItemShimmer() {
    ListItem(
        headlineContent = {
            TextShimmer(Modifier.fillMaxWidth(.3f))
        },
        supportingContent =
        {
            TextShimmer(Modifier.fillMaxWidth(.4f))
        },
        trailingContent = {
            TextShimmer(Modifier.fillMaxWidth(.2f))
        },
        leadingContent = {
            Box(modifier = Modifier
                .border(
                    1.dp,
                    Color.Black, shape = CircleShape
                )
                .padding(5.dp)){
                Icon(
                    Icons.Filled.Person,
                    contentDescription ="",
                    Modifier
                        .size(40.dp),
                )
            }
        }
    )
}