package br.com.popularmovies.datasourceremote.utils

import br.com.popularmovies.datasourceremote.models.base.BaseDto
import br.com.popularmovies.datasourceremote.models.exceptions.NoConnectionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.EOFException
import java.io.IOException


fun <T> networkRequest(response: suspend () -> Response<T>): Flow<Response<T>> {
    return flow{
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
        throw mapResponseException(exception)
    }
}

fun <T> Response<BaseDto<T>>.mapApiResults(): List<T> {
    return try {
    return  with(this) {
        val data = body()
        if (isSuccessful && data != null) {
            data.results
        } else {
            throw Exception() //TODO map http codes with exceptions
        }
    }
    } catch (exception: Exception) {
        throw mapResponseException(exception)
    }
}

fun <T> Flow<Response<T>>.mapApiResult(): Flow<T> {
    return this
        .map { response ->
            response.mapApiResult()
        }
        .catch { throwable ->
            throw mapResponseException(throwable)
        }
}

fun <T> Flow<Response<BaseDto<T>>>.mapApiResults(): Flow<List<T>> {
    return this
        .map { response ->
            response.mapApiResults()
        }
        .catch { throwable ->
            throw mapResponseException(throwable)
        }
}

fun mapResponseException(throwable: Throwable?): Throwable {
    return if ((throwable is IOException) && (throwable !is EOFException)) {
        NoConnectionException(throwable.message, throwable.cause)
    } else {
        Exception()
    }
}
