package com.mobbelldev.gameverse.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @SerializedName("results")
    val results: List<GameResponse>
)
