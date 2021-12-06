package com.jereschneider.test_talana.repository

import android.content.Context
import androidx.room.Room
import com.jereschneider.test_talana.data.PostDB
import com.jereschneider.test_talana.models.Post
import androidx.lifecycle.LiveData


class RepositoryPost(context: Context) {

    val instance =  Room.databaseBuilder(
                    context,
                    PostDB::class.java,
                    "feed"
                    ).allowMainThreadQueries().build()

    fun getAll(): LiveData<List<Post>> {
        return instance.postDao().getAll()
    }

    fun getByID(id : Long): LiveData<Post> {
        return instance.postDao().getByID(id)
    }

    fun insert(listPost : List<Post>) {
        instance.postDao().insert(listPost)
    }

    fun update(listPost : List<Post>) {
        instance.postDao().update(listPost)
    }


}
