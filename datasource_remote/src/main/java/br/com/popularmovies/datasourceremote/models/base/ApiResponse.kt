package br.com.popularmovies.datasourceremote.models.base

import okhttp3.ResponseBody


interface ApiResponse<T> {
    suspend fun result(): Result<T>
    fun success(data: T): Result.Success<T>
    fun error(code: Int, errorBody: ResponseBody?): Result.Error<T>
    fun failure(exception: Exception): Result.Error<T>
}