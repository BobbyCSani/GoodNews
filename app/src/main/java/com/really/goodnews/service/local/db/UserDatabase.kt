package com.really.goodnews.service.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.really.goodnews.model.User
import com.really.goodnews.service.local.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}