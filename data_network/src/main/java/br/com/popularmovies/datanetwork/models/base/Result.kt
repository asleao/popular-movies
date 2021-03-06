package br.com.popularmovies.datanetwork.models.base

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val appError: AppError) : Result<T>()
    object Loading
}
