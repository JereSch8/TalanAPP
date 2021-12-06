package com.jereschneider.test_talana.ui.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jereschneider.test_talana.BuildConfig
import com.jereschneider.test_talana.R
import com.jereschneider.test_talana.models.*
import com.jereschneider.test_talana.repository.RepositoryPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset

class LoginViewModel(val app: Application) : AndroidViewModel(app) {
    private val repoPrefs = RepositoryPreferences(app)
    val SUCCESS = R.raw.success
    val FAILURE = R.raw.failure
    val LOGIN = R.raw.anim_login

    private val _error: MutableLiveData<ResponseError> = MutableLiveData()
    val error: LiveData<ResponseError> = _error
    private val _success: MutableLiveData<Boolean> = MutableLiveData()
    val success: LiveData<Boolean> = _success

    fun saveApiToken(apiToken : String){ repoPrefs.saveApiKey(apiToken) }
    fun saveKeepLogin(b : Boolean) { repoPrefs.saveLoginKeep(b) }
    fun showToast(msg : String) { Toast.makeText(app,msg,Toast.LENGTH_SHORT).show() }

    fun login(login : Login) = viewModelScope.launch(Dispatchers.IO){
        val queue = Volley.newRequestQueue(app)
        val url = "${BuildConfig.SERVER_URL}api/login/"
        val jsonObjectRequest = object: JsonObjectRequest( Method.POST,url, login.toJSON(),
            Response.Listener { response ->
                val loginSuccess = LoginSuccess.fromJSON(response)
                if(loginSuccess.status == "OK"){
                    saveApiToken(loginSuccess.apiToken)
                    _success.value = true
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
        ){}
        queue.add(jsonObjectRequest)
    }

}