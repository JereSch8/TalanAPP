package com.jereschneider.test_talana.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jereschneider.test_talana.models.*
import com.jereschneider.test_talana.repository.RepositoryFavourite
import com.jereschneider.test_talana.repository.RepositoryPost
import com.jereschneider.test_talana.repository.RepositoryUser

class DetailsViewModel(val app: Application) : AndroidViewModel(app){
    private val repoPost : RepositoryPost = RepositoryPost(app.applicationContext)
    private val repoUser : RepositoryUser = RepositoryUser(app.applicationContext)
    private val repoFav : RepositoryFavourite = RepositoryFavourite(app.applicationContext)

    private val _error: MutableLiveData<ResponseError> = MutableLiveData()
    val error: LiveData<ResponseError> = _error

    fun getPostByID(id : Long ) : LiveData<Post> = repoPost.getByID(id)

    fun getUserByID(id : Long ) : LiveData<User> = repoUser.getByID(id)

    fun getFavouriteByID(id : Long ) : LiveData<Favourite> = repoFav.getByID(id)
}