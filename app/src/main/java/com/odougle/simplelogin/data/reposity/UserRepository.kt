package com.odougle.simplelogin.data.reposity

import com.odougle.simplelogin.data.model.User

interface UserRepository {

    fun createUser(registrationViewParams: RegistrationViewParams)

    fun getUser(id: Long): User

    fun login(username: String, password: String): Long
}