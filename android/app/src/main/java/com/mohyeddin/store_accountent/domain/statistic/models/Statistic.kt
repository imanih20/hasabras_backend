package com.mohyeddin.store_accountent.domain.statistic.models

data class Statistic(
    val sale: Int,
    val purchase: Int,
    val profit: Int,
    val month: Int,
    val year: Int
)