package com.jereschneider.test_talana.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jereschneider.test_talana.models.User


@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll() : LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getByID( id : Long ) : LiveData<User>

    @Update
    fun update(user : List<User>)

    @Insert
    fun insert(users : List<User>)

    @Delete
    fun delete(user: User)
}