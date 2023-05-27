package com.mobbelldev.gameverse.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("released")
    val released: String?,

    @SerializedName("background_image")
    val backgroundImage: String?,

    @SerializedName("metacritic")
    val metacritic: Int?,

    @SerializedName("description_raw")
    val descriptionRaw: String
)
