package com.mohyeddin.store_accountent.data.auth.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("phone") val phone: String
)