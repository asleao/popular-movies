package br.com.popularmovies.datasourceremote.utils

import br.com.popularmovies.datasourceremote.models.base.BaseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response


fun <T> networkRequest(response: suspend () -> Response<T>): Flow<Response<T>> {
    return flow {
        emit(response.invoke())
    }
}

fun <T> Response<T>.mapApiResult(): T {
    return try {
        with(this) {
            val data = body()
            if (isSuccessful && data != null) {
                data
            } else {
                throw Exception() //TODO map http codes with exceptions
            }
        }
    } catch (exception: Exception) {
        throw exception
    }
}

fun <T> Response<BaseDto<T>>.mapApiResults(): List<T> {
    return try {
        with(this) {
            val data = body()
            if (isSuccessful && data != null) {
                data.results
            } else {
                throw Exception() //TODO map http codes with exceptions
            }
        }
    } catch (exception: Exception) {
        throw exception
    }
}

fun <T> Flow<Response<T>>.mapApiResult(): Flow<T> {
    return this
        .map { response ->
            response.mapApiResult()
        }
}

fun <T> Flow<Response<BaseDto<T>>>.mapApiResults(): Flow<List<T>> {
    return this
        .map { response ->
            response.mapApiResults()
        }
}
