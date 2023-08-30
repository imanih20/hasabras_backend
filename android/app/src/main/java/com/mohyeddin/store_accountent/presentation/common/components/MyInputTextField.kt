package com.mohyeddin.store_accountent.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.common.withPersianNumbers
import com.mohyeddin.store_accountent.presentation.common.theme.Vazir

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInputTextField(
    value: String,
    onValueChange: (String)->Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine : Boolean = true,
    leadingIcon : @Composable() (()->Unit)? = null,
    trailingIcon : @Composable() (()->Unit)? = null,
    keyboardOptions : KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    supportingText: String = ""
) {
    OutlinedTextField(
        value = value.withPersianNumbers(),
        onValueChange = onValueChange,
        modifier= modifier.fillMaxWidth(),
        label = {
            MyText(text = label)
        },
        shape = RoundedCornerShape(10.dp),
        textStyle = textStyle.copy(fontFamily = Vazir),
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        isError = isError,
        supportingText = {
            MyText(text = supportingText)
        },      
    )
}