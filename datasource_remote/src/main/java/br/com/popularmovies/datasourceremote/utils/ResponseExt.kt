package br.com.popularmovies.datasourceremote.utils

import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import retrofit2.Response

suspend fun <T> Response<T>.request() = RetrofitResponse<T>().request(this)