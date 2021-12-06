package com.jereschneider.test_talana.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jereschneider.test_talana.models.Post


@Database(
    entities = [Post::class],
    version = 1
)
abstract class PostDB : RoomDatabase() {

    abstract fun postDao() : PostDao

}