package com.mohyeddin.store_accountent.common

import kotlin.text.StringBuilder

fun String.withPersianNumbers() : String{
    val builder = StringBuilder()
    for (c in this.toCharArray()){
        if(c.isDigit()){
            when(c){
                '0'-> builder.append('۰')
                '1'-> builder.append('۱')
                '2'-> builder.append('۲')
                '3'-> builder.append('۳')
                '4'-> builder.append('۴')
                '5'-> builder.append('۵')
                '6'-> builder.append('۶')
                '7'-> builder.append('۷')
                '8'-> builder.append('۸')
                '9'-> builder.append('۹')
                else->builder.append(c)
            }
        }else{
            builder.append(c)
        }
    }
    return builder.toString()
}

fun String.withEnglishNumber() : String{
    val builder = StringBuilder()
    for (c in this.toCharArray()){
        if(c.isDigit()){
            when(c){
                '۰'-> builder.append('0')
                '۱'-> builder.append('1')
                '۲'-> builder.append('2')
                '۳'-> builder.append('3')
                '۴'-> builder.append('4')
                '۵'-> builder.append('5')
                '۶'-> builder.append('6')
                '۷'-> builder.append('7')
                '۸'-> builder.append('8')
                '۹'-> builder.append('9')
                else->builder.append(c)
            }
        }else{
            builder.append(c)
        }
    }
    return builder.toString()
}
fun String.addNumberSeparator() : String {
    val chars = toCharArray()
    val builder = StringBuilder()
    var i = chars.size - 1
    var counter = 0
    while (i >= 0) {
        if (chars[i] == '،') continue
        if (chars[i] == '.') break
        counter++
        builder.append(chars[i])
        if (counter % 3 == 0 && i > 0) {
            builder.append("،")
        }
        i--
    }
    return builder.reverse().toString()
}

