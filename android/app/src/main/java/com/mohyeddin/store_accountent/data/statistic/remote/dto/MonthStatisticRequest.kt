package com.mohyeddin.store_accountent.data.statistic.remote.dto

import com.google.gson.annotations.SerializedName

data class MonthStatisticRequest(
    @SerializedName("isPaid") val isPaid: Boolean
)