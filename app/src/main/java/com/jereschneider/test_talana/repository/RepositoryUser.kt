package com.jereschneider.test_talana.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.jereschneider.test_talana.data.UserDB
import com.jereschneider.test_talana.models.User

class RepositoryUser (context: Context) {
    private val instance =  Room.databaseBuilder(
        context,
        UserDB::class.java,
        "contacts"
    ).allowMainThreadQueries().build()

    fun getAll(): LiveData<List<User>> {
        return instance.userDao().getAll()
    }

    fun getByID(id : Long): LiveData<User> {
        return instance.userDao().getByID(id)
    }

    fun insert(listUser : List<User>) {
        instance.userDao().insert(listUser)
    }

    fun update(listUser : List<User>) {
        instance.userDao().update(listUser)
    }

}