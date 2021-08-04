package com.odougle.simplelogin.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.odougle.simplelogin.ui.registration.RegistrationViewParams

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Long = 0,
    val name: String,
    val bio: String,
    val username: String,
    val password: String
)

fun RegistrationViewParams.toUserEntity(): UserEntity{
    return with(this){
        UserEntity(
            name = this.name,
            bio = this.bio,
            username = this.username,
            password = this.password
        )
    }
}