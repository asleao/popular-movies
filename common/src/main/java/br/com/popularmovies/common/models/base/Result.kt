package br.com.popularmovies.common.base

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: br.com.popularmovies.common.base.Error) : Result<T>()
    object Loading
}
