package com.odougle.simplelogin.data.reposity

import com.odougle.simplelogin.data.db.dao.UserDao
import com.odougle.simplelogin.data.db.toUser
import com.odougle.simplelogin.data.db.toUserEntity
import com.odougle.simplelogin.data.model.User
import com.odougle.simplelogin.ui.registration.RegistrationViewParams

class UserDbDataSource(
    private val userDao: UserDao
): UserRepository {
    override fun createUser(registrationViewParams: RegistrationViewParams) {
        userDao.save(registrationViewParams.toUserEntity())
    }

    override fun getUser(id: Long): User {
        return userDao.getUser(id).toUser()
    }

    override fun login(username: String, password: String): Long {
        return userDao.login(username,password)
    }
}