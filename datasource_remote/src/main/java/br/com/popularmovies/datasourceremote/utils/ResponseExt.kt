package br.com.popularmovies.datasourceremote.utils

import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import com.squareup.moshi.Moshi
import retrofit2.Response

suspend fun <T> Response<T>.request(moshi: Moshi) = RetrofitResponse<T>(moshi).request(this)