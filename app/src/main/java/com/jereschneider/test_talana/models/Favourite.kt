package com.jereschneider.test_talana.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Favourite(
    @PrimaryKey
    @SerializedName("id_post")val id_post: Long,
)
