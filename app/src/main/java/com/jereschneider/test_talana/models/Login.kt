package com.jereschneider.test_talana.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class Login(
    @SerializedName("username")val username: String,
    @SerializedName("password")val password: String,
){
    fun toJSON(): JSONObject {
        val jo = JSONObject()
        jo.put("username", username)
        jo.put("password", password)
        return jo
    }
}

data class LoginSuccess(
    @SerializedName("STATUS")val status: String,
    @SerializedName("api-token")val apiToken: String,
){
    companion object {
        fun fromJSON(jsonObject: JSONObject) : LoginSuccess{
            return Gson().fromJson(jsonObject.toString(), LoginSuccess::class.java)
        }
    }
}

data class LoginFailure(
    @SerializedName("STATUS")val status: String,
    @SerializedName("message")val message: String,
){
    companion object {
        fun fromJSON(jsonObject: String) : LoginFailure{
            return Gson().fromJson(jsonObject, LoginFailure::class.java)
        }
    }
}
