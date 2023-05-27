package com.mobbelldev.gameverse.core.data.source.remote

import android.util.Log
import com.mobbelldev.gameverse.core.data.source.remote.network.ApiResponse
import com.mobbelldev.gameverse.core.data.source.remote.network.ApiService
import com.mobbelldev.gameverse.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllGames(): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.getGames()
                if (response.results.isNotEmpty()) emit(ApiResponse.Success(response.results)) else emit(
                    ApiResponse.Empty
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getAllGames: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailGameById(id: Int): Flow<ApiResponse<GameResponse>> {
        return flow {
            try {
                val response = apiService.getDetailGameById(id.toString())
                if (response.descriptionRaw != "") emit(ApiResponse.Success(response)) else emit(
                    ApiResponse.Empty
                )
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getDetailGameById: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchGame(query: String): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.searchGames(query)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "searchGame: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private val TAG = RemoteDataSource::class.simpleName
    }
}