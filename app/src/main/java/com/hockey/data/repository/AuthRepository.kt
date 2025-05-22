// data/repository/AuthRepository.kt
package com.hockey.data.repository

import com.hockey.data.dao.UserDao
import com.hockey.data.entities.User
import javax.inject.Inject

class AuthRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): User? {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.password == password) user else null
    }

    suspend fun register(user: User): Long {
        return userDao.insertUser(user)
    }
}