package com.vvechirko.newrepository.data

import com.google.gson.annotations.SerializedName

class UserEntity(
    val id: Int,

    val email: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    val avatar: String
) {
    val fullName: String
        get() = "$firstName $lastName"
}