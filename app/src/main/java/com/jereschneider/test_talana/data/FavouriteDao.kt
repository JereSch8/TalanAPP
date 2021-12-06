package com.jereschneider.test_talana.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jereschneider.test_talana.models.Favourite


@Dao
interface FavouriteDao {
    @Query("SELECT * FROM Favourite")
    fun getAll() : LiveData<List<Favourite>>

    @Query("SELECT * FROM Favourite WHERE id_post = :id")
    fun getByID( id : Long ) : LiveData<Favourite>

    @Update
    fun update(favourite : Favourite)

    @Insert
    fun insert(favourite : Favourite)

    @Delete
    fun delete(favourite: Favourite)
}