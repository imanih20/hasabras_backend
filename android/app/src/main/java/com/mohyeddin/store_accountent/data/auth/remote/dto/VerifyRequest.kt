package com.mohyeddin.store_accountent.data.auth.remote.dto

import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @SerializedName("token") val token: String
)