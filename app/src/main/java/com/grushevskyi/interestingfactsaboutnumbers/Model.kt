package com.grushevskyi.interestingfactsaboutnumbers

import com.google.gson.annotations.SerializedName

data class Model (
    @SerializedName("text")
    val text: String,
    @SerializedName("found")
    val found: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("year")
    val year: String
    )