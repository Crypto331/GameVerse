package com.mobbelldev.gameverse.core.domain.usecase

import com.mobbelldev.gameverse.core.utils.Resource
import com.mobbelldev.gameverse.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getGames(): Flow<Resource<List<Game>>>
    fun getFavoriteGames(): Flow<List<Game>>
    fun getDetailGameById(id: Int): Flow<Resource<Game>>
    fun setFavoriteGame(game: Game)
    suspend fun searchGames(query: String): Resource<List<Game>>?
    suspend fun insertGame(game: Game)
}