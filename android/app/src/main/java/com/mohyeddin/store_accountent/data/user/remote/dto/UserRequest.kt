package com.mohyeddin.store_accountent.data.user.remote.dto

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name") val name: String
)