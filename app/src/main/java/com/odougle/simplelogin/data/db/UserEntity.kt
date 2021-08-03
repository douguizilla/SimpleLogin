package com.odougle.simplelogin.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Long = 0,
    val name: String,
    val bio: String,
    val username: String,
    val password: String
)