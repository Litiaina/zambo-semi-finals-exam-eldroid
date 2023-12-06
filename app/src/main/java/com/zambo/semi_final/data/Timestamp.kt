package com.zambo.semi_final.data

import com.google.gson.annotations.SerializedName

data class Timestamp(
    @SerializedName("_seconds") val seconds: Long,
    @SerializedName("_nanoseconds") val nanoseconds: Long
)
