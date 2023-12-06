package com.zambo.semi_final.data

import com.google.gson.annotations.SerializedName

data class Tweet(
    val id: String,
    val name: String,
    val description: String,
    @SerializedName("timestamp") val timestamp: Timestamp
)
