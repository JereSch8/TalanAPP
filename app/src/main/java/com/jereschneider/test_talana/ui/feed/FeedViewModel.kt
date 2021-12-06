package com.jereschneider.test_talana.ui.feed

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jereschneider.test_talana.BuildConfig
import com.jereschneider.test_talana.models.*
import com.jereschneider.test_talana.repository.RepositoryFavourite
import com.jereschneider.test_talana.repository.RepositoryPost
import com.jereschneider.test_talana.repository.RepositoryPreferences
import com.jereschneider.test_talana.repository.RepositoryUser
import com.jereschneider.test_talana.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset

class FeedViewModel(val app: Application) : AndroidViewModel(app){
    private val repoPost : RepositoryPost = RepositoryPost(app)
    private val repoUser : RepositoryUser = RepositoryUser(app)
    private val repoFav : RepositoryFavourite = RepositoryFavourite(app)
    private val repoPrefs = RepositoryPreferences(app)

    val posts = repoPost.getAll()
    val users = repoUser.getAll()
    val favourites = repoFav.getAll()
    private val _error: MutableLiveData<ResponseError> = MutableLiveData()
    val error: LiveData<ResponseError> = _error

    fun getPosts() = viewModelScope.launch(Dispatchers.IO) {
        val queue = Volley.newRequestQueue(app)
        val url = "${BuildConfig.SERVER_URL}api/feed/"
        val jsonObjectRequest = JsonArrayRequest(url,
            { response ->
                try{
                    repoPost.insert(Post.fromJSON(response))
                }catch ( e : android.database.sqlite.SQLiteConstraintException){
                    repoPost.update(Post.fromJSON(response))
                }
            },
            { error ->
                try {
                    val body = String(
                        error.networkResponse.data,
                        Charset.forName("UTF-8"))
                    _error.value = ResponseError.fromJSON(body)

                }
                catch (e : Error){ _error.value = ResponseError("Invalid response") }
                catch (e : java.lang.NullPointerException){
                    _error.value = ResponseError("Error with network")
                }
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        val queue = Volley.newRequestQueue(app)
        val url = "${BuildConfig.SERVER_URL}api/contacts/"
        val jsonObjectRequest = JsonArrayRequest(url,
            { response ->
                Log.e(TAG, "ENTRO", )
                try{
                    repoUser.insert(User.fromJSON(response))
                }catch ( e : android.database.sqlite.SQLiteConstraintException){
                    repoUser.update(User.fromJSON(response))
                }
            },
            { error ->
                try {
                    val body = String(
                        error.networkResponse.data,
                        Charset.forName("UTF-8"))
                    _error.value = ResponseError.fromJSON(body)

                }
                catch (e : Error){ _error.value = ResponseError("Invalid response") }
                catch (e : java.lang.NullPointerException){
                    _error.value = ResponseError("Error with network")
                }
            })
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun setFavourite(postId : Long) = viewModelScope.launch(Dispatchers.IO){
        val setFavourite = SetFavourite(repoPrefs.getApiKey(),postId)
        val queue = Volley.newRequestQueue(app)
        val url = "${BuildConfig.SERVER_URL}api/favorite/"
        val jsonObjectRequest = object: JsonObjectRequest( Method.POST,url, setFavourite.toJSON(),
            Response.Listener { response ->
                if(response["STATUS"] == "OK"){
                    try{ repoFav.insert(Favourite(postId)) }
                    catch ( e : SQLiteConstraintException){
                        repoFav.delete(Favourite(postId))
                    }
                }
                else _error.value = ResponseError("Invalid credentials")
            },
            Response.ErrorListener { error ->
                try {
                    val body = String(
                        error.networkResponse.data,
                        Charset.forName("UTF-8"))
                    _error.value = ResponseError.fromJSON(body)

                }
                catch (e : Error){ _error.value = ResponseError("Invalid response") }
                catch (e : NullPointerException){
                    _error.value = ResponseError("Error with network")
                }
            }
        )
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["apikey"] = setFavourite.apikey
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }
}