package com.mohyeddin.store_accountent.presentation.trade.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohyeddin.store_accountent.R
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField

@Composable
fun NumberAdder(
    number: Double,
    unit: String,
    min: Double = 0.0,
    max: Double = 100.0,
    onNumberChange : (Double)->Unit
) {
    var baseNumber by remember {
        mutableStateOf(number)
    }
    Row(verticalAlignment = Alignment.Bottom) {
        FilledIconButton(onClick = {
            if (baseNumber == max){
                baseNumber = min
            }else {
                if (unit == "w") baseNumber += 0.1
                else baseNumber ++
            }
            onNumberChange(baseNumber)
        }) {
            Icon(Icons.Default.Add, contentDescription = "")
        }
        Spacer(modifier = Modifier.size(5.dp))
        MyInputTextField(
            value = if(unit == "n") baseNumber.toInt().toString() else baseNumber.toString(),
            onValueChange = {
                baseNumber = if (it.isEmpty()) min
                else if (it.toDouble() > max) max
                else if (it.toDouble()<min) min
                else {
                    it.toDouble()
                }
            },
            textStyle = MaterialTheme.typography.titleMedium .copy(color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center),
            modifier = Modifier
                .height(TextFieldDefaults.MinHeight + 3.dp)
                .aspectRatio(1.3f, true),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = if(unit == "n") "تعداد" else "مقدار"
        )

        Spacer(modifier = Modifier.size(5.dp))
        FilledIconButton(onClick = {
            if (baseNumber == min){
                baseNumber = max
            }else {
                if (unit == "w") baseNumber -= 0.1
                else baseNumber --
            }
            onNumberChange(baseNumber)
        }) {
            Icon(painter = painterResource(id = R.drawable.minuse), contentDescription = "")
        }
    }
}
