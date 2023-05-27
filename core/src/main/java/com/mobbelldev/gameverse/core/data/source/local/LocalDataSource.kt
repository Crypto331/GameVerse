package com.mobbelldev.gameverse.core.data.source.local

import com.mobbelldev.gameverse.core.data.source.local.entity.GameEntity
import com.mobbelldev.gameverse.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val gameDao: GameDao) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    fun getBookmarkGames(): Flow<List<GameEntity>> = gameDao.getBookmarkGames()

    fun getGameById(id: Int): Flow<GameEntity>? = gameDao.getGameById(id)

    suspend fun insertAllGames(games: List<GameEntity>) = gameDao.insertAllGames(games)

    suspend fun insertGame(game: GameEntity) = gameDao.insertGame(game)

    fun updateGame(game: GameEntity) = gameDao.updateGame(game)
}