package com.mobbelldev.gameverse.core.domain.repository

import com.mobbelldev.gameverse.core.data.mapper.DataMapper
import com.mobbelldev.gameverse.core.data.source.local.LocalDataSource
import com.mobbelldev.gameverse.core.data.source.remote.RemoteDataSource
import com.mobbelldev.gameverse.core.data.source.remote.network.ApiResponse
import com.mobbelldev.gameverse.core.data.source.remote.network.resourcebound.networkBoundResource
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.utils.AppExecutors
import com.mobbelldev.gameverse.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {
    override fun getGames(): Flow<Resource<List<Game>>> =
        networkBoundResource(
            loadFromDB = {
                localDataSource.getAllGames().map { DataMapper.mapEntitiesToDomain(it) }
            },
            shouldFetch = { data -> data.isEmpty() },
            createCall = { remoteDataSource.getAllGames() },
            saveCallResult = { data ->
                localDataSource.insertAllGames(
                    DataMapper.mapResponsesToEntities(
                        data
                    )
                )
            }
        )

    override fun getFavoriteGames(): Flow<List<Game>> =
        localDataSource.getBookmarkGames().map { DataMapper.mapEntitiesToDomain(it) }

    override fun getDetailGameById(id: Int): Flow<Resource<Game>> =
        networkBoundResource(
            loadFromDB = {
                localDataSource.getGameById(id)?.map { DataMapper.mapEntityToDomain(it) }
            },
            shouldFetch = { data -> data.description.isEmpty() },
            createCall = { remoteDataSource.getDetailGameById(id) },
            saveCallResult = { data ->
                localDataSource.insertAllGames(
                    DataMapper.mapResponseToEntity(
                        data
                    )
                )
            }
        )

    override fun setFavoriteGame(game: Game) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        gameEntity.isFavorite = !gameEntity.isFavorite
        appExecutors.diskIO().execute { localDataSource.updateGame(gameEntity) }
    }

    override suspend fun searchGames(query: String): Resource<List<Game>> {
        return when (val response = remoteDataSource.searchGame(query).first()) {
            is ApiResponse.Success -> {
                val gameEntities = DataMapper.mapResponsesToEntities(response.data)
                val games = DataMapper.mapEntitiesToDomain(gameEntities)
                Resource.Success(games)
            }

            is ApiResponse.Error -> Resource.Error(response.errorMessage, null)

            is ApiResponse.Empty -> Resource.Error(response.toString(), null)
        }
    }

    override suspend fun insertGame(game: Game) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        localDataSource.insertGame(gameEntity)
    }


}