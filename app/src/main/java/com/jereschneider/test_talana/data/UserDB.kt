package com.jereschneider.test_talana.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jereschneider.test_talana.models.User


@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDB : RoomDatabase(){
    abstract fun userDao() : UserDao
}