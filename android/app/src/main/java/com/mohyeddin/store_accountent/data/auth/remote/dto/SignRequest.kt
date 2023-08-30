package com.mohyeddin.store_accountent.data.auth.remote.dto

import com.google.gson.annotations.SerializedName

data class SignRequest(
    @SerializedName("name") val name : String,
    @SerializedName("phone") val phone: String
)