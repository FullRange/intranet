package com.akra.intranet.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

fun <T> handleApiRequest(apiRequest: suspend () -> T): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading())
        val response = apiRequest.invoke()
        emit(Resource.Success(response))
    } catch (e: Exception) {
        emit(Resource.Error(e.message.orEmpty()))
    }
}
