package com.mohyeddin.store_accountent.data.market.remote.dto

import com.google.gson.annotations.SerializedName

data class MarketResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val ownerId: String
)