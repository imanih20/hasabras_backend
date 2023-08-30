package com.mohyeddin.store_accountent.presentation.trade.components

import androidx.compose.runtime.Composable
import com.mohyeddin.store_accountent.presentation.common.components.MyDialog
import com.mohyeddin.store_accountent.presentation.common.components.MyText

@Composable
fun DeleteDialog(onDismissRequest:()->Unit,onPosClick: () -> Unit) {
    MyDialog(
        onDismissRequest = onDismissRequest,
        positiveBtnTxt = "بله",
        negativeBtnText = "خیر",
        onPositiveBtnClick = {
            onPosClick()
            onDismissRequest()
        }
    ) {
        MyText(text = "آیا از حذف این ایتم مطمئنید؟")
    }
}