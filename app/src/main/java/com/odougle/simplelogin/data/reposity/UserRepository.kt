package com.odougle.simplelogin.data.reposity

import com.odougle.simplelogin.data.model.User
import com.odougle.simplelogin.ui.registration.RegistrationViewParams

interface UserRepository {

    suspend fun createUser(registrationViewParams: RegistrationViewParams)

    fun getUser(id: Long): User

    fun login(username: String, password: String): Long
}