package com.jereschneider.test_talana.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

@Entity
data class User (
    @PrimaryKey
    @SerializedName("id") val id: Long,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("gender") val gender: String
){
    companion object {
        fun fromJSON(jsonArray: JSONArray) : List<User>{
            return Gson().fromJson(
                jsonArray.toString(),
                Array<User>::class.java
            ).toList()
        }
    }
}

