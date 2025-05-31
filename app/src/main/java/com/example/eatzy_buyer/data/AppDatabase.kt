package com.example.eatzy.buyer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eatzy_buyer.data.dao.UserDao
import com.example.eatzy_buyer.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}