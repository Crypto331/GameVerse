package com.mobbelldev.gameverse.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mobbelldev.gameverse.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE isFavorite = 1")
    fun getBookmarkGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGameById(id: Int): Flow<GameEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGames(game: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Update
    fun updateGame(game: GameEntity)
}