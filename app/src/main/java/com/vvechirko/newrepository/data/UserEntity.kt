package com.vvechirko.newrepository.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val email: String,

    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    val firstName: String,

    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    val lastName: String,

    val avatar: String
) {
    val fullName: String
        get() = "$firstName $lastName"
}