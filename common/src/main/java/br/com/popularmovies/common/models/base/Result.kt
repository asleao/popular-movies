package br.com.popularmovies.common.models.base

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: NetworkError) : Result<T>()
    object Loading
}
