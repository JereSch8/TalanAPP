package com.jereschneider.test_talana.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class SetFavourite(
    @SerializedName("apikey")val apikey: String,
    @SerializedName("postid")val postid: Long,
){
    fun toJSON(): JSONObject {
        val jo = JSONObject()
        jo.put("apikey", apikey)
        jo.put("postid", postid)
        return jo
    }
}