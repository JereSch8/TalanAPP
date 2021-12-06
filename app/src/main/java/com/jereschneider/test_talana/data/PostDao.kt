package com.jereschneider.test_talana.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jereschneider.test_talana.models.Post


@Dao
interface PostDao {

    @Query("SELECT * FROM Post")
    fun getAll() : LiveData<List<Post>>

    @Query("SELECT * FROM Post WHERE id = :id")
    fun getByID( id : Long ) : LiveData<Post>

    @Update
    fun update(post : List<Post>)

    @Insert
    fun insert(posts : List<Post>)

    @Delete
    fun delete(post: Post)
}