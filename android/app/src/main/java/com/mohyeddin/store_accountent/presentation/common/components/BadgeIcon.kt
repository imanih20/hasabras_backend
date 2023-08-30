package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeIcon(icon: ImageVector,number: Int = 0,onClick: ()->Unit) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            BadgedBox(badge = {
                Badge {
                    MyText(text = number.toString() )
                }
            }, modifier = Modifier.padding(5.dp).clickable { onClick() }) {
                IconBox(imageVector = icon)
            }
        }
}