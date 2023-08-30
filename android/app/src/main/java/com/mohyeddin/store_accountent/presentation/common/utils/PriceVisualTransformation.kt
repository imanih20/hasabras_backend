package com.mohyeddin.store_accountent.presentation.common.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.mohyeddin.store_accountent.common.withPersianNumbers

class PriceVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        // Make the string YYYY-MM-DD
        val trimmed = text.text.reversed().trim()
        var output = ""
        for (i in trimmed.indices) {
            if (i>2 && i%3==0) output += "،"
            output += trimmed[i]
        }
        val priceTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 3) offset
                else  offset -1 + offset/3
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 3) offset
                else offset - offset/3
            }

        }

        return TransformedText(
            AnnotatedString(output.reversed().withPersianNumbers()),
            priceTranslator
        )
    }
}