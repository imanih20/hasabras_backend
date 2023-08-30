package com.mohyeddin.store_accountent.data.market.remote.dto

import com.google.gson.annotations.SerializedName

data class MarketRequest(
    @SerializedName("name") val name: String
)