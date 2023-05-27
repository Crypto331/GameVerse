package com.mobbelldev.gameverse.core.data.source.remote.network.resourcebound

import com.mobbelldev.gameverse.core.data.source.remote.network.ApiResponse
import com.mobbelldev.gameverse.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDB: () -> Flow<ResultType>?,
    crossinline shouldFetch: (ResultType) -> Boolean,
    crossinline createCall: suspend () -> Flow<ApiResponse<RequestType>>,
    crossinline saveCallResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: () -> Unit = {}
): Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading())

    val dbSource = loadFromDB()?.first()

    if (dbSource?.let { shouldFetch(it) } == true) {
        emit(Resource.Loading())

        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                saveCallResult(apiResponse.data)
            }

            is ApiResponse.Empty -> {}

            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error(apiResponse.errorMessage))
                return@flow
            }
        }
    }

    loadFromDB()?.map { Resource.Success(it) }?.collect {
        emit(it)
    }
}