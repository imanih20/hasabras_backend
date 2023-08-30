package com.mohyeddin.store_accountent.presentation.common.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.mohyeddin.store_accountent.common.withPersianNumbers

class   DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Make the string YYYY-MM-DD
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        var output = ""
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i >= 3 && i % 2 == 1 && i < 7) output += "-"
        }
        val dateTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // [offset [0 - 3] remain the same]
                if (offset <= 3) return offset
                // [4 - 5] transformed to [5 - 6] respectively
                if (offset <= 5) return offset + 1
                // [6 - 7] transformed to [8 - 9] respectively
                if (offset <= 7) return offset + 2
                return 10
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 6) return offset - 1
                if (offset <= 9) return offset - 2
                return 8
            }

        }

        return TransformedText(
            AnnotatedString(output.withPersianNumbers()),
            dateTranslator
        )
    }
}