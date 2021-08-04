package com.odougle.simplelogin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.odougle.simplelogin.data.db.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserEntity) //min 20

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Long): UserEntity

    @Query("SELECT id FROM user WHERE username = :username and password = :password")
    fun login(username: String, password: String): Long
}