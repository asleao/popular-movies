package br.com.popularmovies.datasourceremote.models.base

import br.com.popularmovies.common.models.base.Result
import okhttp3.ResponseBody
import retrofit2.Response


interface ApiResponse<T> {
    suspend fun request(response: Response<T>): Result<T>
    fun success(data: T): Result.Success<T>
    fun error(code: Int, errorBody: ResponseBody?): Result.Error<T>
    fun failure(exception: Exception): Result.Error<T>
}