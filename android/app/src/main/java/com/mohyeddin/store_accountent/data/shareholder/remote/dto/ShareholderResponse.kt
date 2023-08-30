package com.mohyeddin.store_accountent.data.shareholder.remote.dto

import com.google.gson.annotations.SerializedName

data class ShareholderResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("share") val share: Double,
)