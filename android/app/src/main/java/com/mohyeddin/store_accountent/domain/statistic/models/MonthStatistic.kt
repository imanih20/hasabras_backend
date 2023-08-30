package com.mohyeddin.store_accountent.domain.statistic.models

data class MonthStatistic(
    val id: String,
    val shareholderId: String,
    val purchase: Int,
    val sale: Int,
    val profit: Int,
    val year: Int,
    val month: Int,
    val isPaid: Boolean,
)