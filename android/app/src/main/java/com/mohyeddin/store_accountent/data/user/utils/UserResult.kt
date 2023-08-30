package com.mohyeddin.store_accountent.data.user.utils

import com.mohyeddin.store_accountent.data.common.utils.ResultMaker
import com.mohyeddin.store_accountent.data.user.remote.dto.UserResponse
import com.mohyeddin.store_accountent.domain.user.model.User

class UserResult : ResultMaker<User,UserResponse>() {
    override fun getModel(res: UserResponse): User {
        return User(res.id,res.name,res.phone)
    }
}