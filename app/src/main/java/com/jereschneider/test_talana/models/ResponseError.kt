package com.jereschneider.test_talana.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ResponseError(@SerializedName("message")
                         val message: String){
    companion object {
        fun fromJSON(jsonObject: String) : ResponseError{
            return Gson().fromJson(jsonObject, ResponseError::class.java)
        }
    }
}
