package com.mobbelldev.gameverse.core.data.source.remote.network

import com.mobbelldev.gameverse.core.data.source.remote.response.GameResponse
import com.mobbelldev.gameverse.core.data.source.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // get all games
    @GET("games")
    suspend fun getGames(): GamesResponse

    // get detail games
    @GET("games/{gameId}")
    suspend fun getDetailGameById(@Path("gameId") gameId: String): GameResponse

    // get games by name
    @GET("games")
    suspend fun searchGames(@Query("search") search: String): GamesResponse
}