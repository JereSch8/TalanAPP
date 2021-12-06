package com.jereschneider.test_talana.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.jereschneider.test_talana.data.FavouriteDB
import com.jereschneider.test_talana.models.Favourite

class RepositoryFavourite (context: Context) {
    private val instance =  Room.databaseBuilder(
        context,
        FavouriteDB::class.java,
        "favourites"
    ).allowMainThreadQueries().build()

    fun getAll(): LiveData<List<Favourite>> {
        return instance.favouriteDao().getAll()
    }

    fun getByID(id : Long): LiveData<Favourite> {
        return instance.favouriteDao().getByID(id)
    }

    fun insert(favourite : Favourite) {
        instance.favouriteDao().insert(favourite)
    }

    fun update(favourite : Favourite) {
        instance.favouriteDao().update(favourite)
    }

    fun delete(favourite : Favourite) {
        instance.favouriteDao().delete(favourite)
    }

}