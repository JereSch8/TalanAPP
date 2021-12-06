package com.jereschneider.test_talana.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jereschneider.test_talana.models.Favourite

@Database(
    entities = [Favourite::class],
    version = 1
)
abstract class FavouriteDB : RoomDatabase() {

    abstract fun favouriteDao() : FavouriteDao

}