package br.com.popularmovies.datasourceremote.models.base

import br.com.popularmovies.common.models.base.Result
import okhttp3.ResponseBody
import retrofit2.Response


interface ApiResponse {
    suspend fun <T> request(response: suspend () -> Response<T>): Result<T>
    fun <T> success(data: T): Result.Success<T>
    fun <T> error(code: Int, errorBody: ResponseBody?): Result.Error<T>
    fun <T> failure(exception: Exception): Result.Error<T>
}