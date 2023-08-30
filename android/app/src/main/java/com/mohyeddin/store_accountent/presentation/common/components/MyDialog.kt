package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MyDialog(onDismissRequest: ()->Unit,onPositiveBtnClick:(()->Unit)?=null,positiveBtnTxt: String = "",negativeBtnText: String = "",content: @Composable (()->Unit)) {
    Dialog(onDismissRequest = onDismissRequest) {
            Column {
                Surface(shape = RoundedCornerShape(10.dp)) {
                    Box(modifier = Modifier.padding(10.dp).sizeIn(minWidth = 100.dp, minHeight = 100.dp)){
                        content()
                    }
                }
                Spacer(modifier = Modifier.size(1.dp))
                Row {
                    if (positiveBtnTxt.isNotEmpty()) Surface(shape = RoundedCornerShape(10.dp)) {
                        TextButton(onClick = {
                            if (onPositiveBtnClick != null) {
                                onPositiveBtnClick()
                                onDismissRequest()
                            }
                        }) {
                            MyText(text = positiveBtnTxt)
                        }
                    }
                    if (negativeBtnText.isNotEmpty() || positiveBtnTxt.isNotEmpty())
                        Spacer(modifier = Modifier.size(2.dp))
                    if (negativeBtnText.isNotEmpty()) Surface(shape = RoundedCornerShape(10.dp)) {
                        TextButton(onClick = { onDismissRequest() }) {
                            MyText(text = negativeBtnText)
                        }
                    }
                }
            }
        }
}