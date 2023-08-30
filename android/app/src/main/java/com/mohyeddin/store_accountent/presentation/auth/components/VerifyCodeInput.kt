package com.mohyeddin.store_accountent.presentation.auth.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.res.TypedArrayUtils
import com.mohyeddin.store_accountent.common.withPersianNumbers
import com.mohyeddin.store_accountent.presentation.common.components.MyInputTextField

@Composable
fun VerifyCodeInput(
    digits: Int,
    onCodeChange: (String)->Unit,
    modifier: Modifier = Modifier
) {
    val code = remember {
        mutableStateListOf<String>()
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        LazyRow(
            modifier = modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(digits){index->
                SingleCodeInput(
                    onValueChange = {
                        if (it.isEmpty()){
                            code.removeAt(index)
                        }
                        code.add(index,it)
                        onCodeChange(code.joinToString(""))
                    },
                    Modifier
                        .height(TextFieldDefaults.MinHeight)
                        .aspectRatio(1f, true)
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SingleCodeInput(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var code1 by remember {
        mutableStateOf("")
    }
    val fm = LocalFocusManager.current
    MyInputTextField(
        value = code1.withPersianNumbers(),
        onValueChange = {
            if(it.length > 1) {
                return@MyInputTextField
            }else if(it.length == 1){
                fm.moveFocus(FocusDirection.Next)
            }
            code1 = it
            onValueChange(code1)
        },
        modifier = modifier
            .focusGroup()
            .onKeyEvent {
                if (it.key == Key.Backspace) {
                    code1 = ""
                    fm.moveFocus(FocusDirection.Previous)
                    true
                } else {
                    false
                }
            }
        ,
        textStyle = TextStyle(textAlign = TextAlign.Center),
        singleLine = true,
    )
}

@Preview
@Composable
fun VerifyCodePreview() {
    var code by remember {
        mutableStateOf("")
    }
    Surface(Modifier.padding(10.dp)) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("code: $code")
                VerifyCodeInput(
                    4,
                    {code = it},
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}
