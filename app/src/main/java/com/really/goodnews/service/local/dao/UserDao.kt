package com.really.goodnews.service.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.really.goodnews.model.User

@Dao
interface UserDao {

    @Query("select * FROM user")
    fun getUsers(): List<User>

    @Query("select * FROM user Where id= :id")
    fun getUserById(id: Int): User

    @Query("select * FROM user Where email = :email")
    fun getUserByEmail(email: String): User

    @Insert
    fun insertUsers(user: User)

}