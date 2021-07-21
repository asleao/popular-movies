package br.com.popularmovies.datasourceremote.utils

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.base.BaseDto

fun <T> Result<out T>.mapNetworkResult(): Result<T> {
    return when (this) {
        is Result.Success -> Result.Success(data)
        is Result.Error -> Result.Error(error)
    }
}

fun <T> Result<out BaseDto<T>>.mapNetworkResults(): Result<List<T>> {
    return when (this) {
        is Result.Success -> Result.Success(data.results)
        is Result.Error -> Result.Error(error)
    }
}