package br.com.popularmovies.services.movieService.source

import android.util.Log
import br.com.popularmovies.core.network.CONNECTION_MSG_ERROR
import br.com.popularmovies.core.network.GENERIC_ERROR_CODE
import br.com.popularmovies.core.network.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.core.network.NETWORK_ERROR_CODE
import br.com.popularmovies.data.model.ErrorResponse
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.movies.Constants.SERVER_MSG_ERROR
import com.squareup.moshi.Moshi
import retrofit2.Response
import java.io.IOException

class ApiResponse<T>(private val logTag: String) {

    fun getApiOnResponse(response: Response<T>): OldResource<T> {
        if (response.body() != null) {
            if (response.isSuccessful) {
                return OldResource.success(response.body())
            } else {
                if (response.errorBody() != null) {
                    var error: ErrorResponse? = null
                    if (response.code() in 400 until GENERIC_ERROR_CODE) {
                        val moshi = Moshi.Builder().build()
                        val jsonAdapter = moshi.adapter(ErrorResponse::class.java)
                        try {
                            error = jsonAdapter.fromJson(response.errorBody()!!.string())
                        } catch (e: IOException) {
                            Log.e(logTag, e.message)
                        }
                    } else {
                        error = ErrorResponse(
                            response.code(),
                            SERVER_MSG_ERROR
                        )
                    }
                    return OldResource.error(error)
                }
            }
        }

        return OldResource.error(
            ErrorResponse(
                response.code(),
                SERVER_MSG_ERROR
            )
        )
    }

    fun getApiOnFailure(t: Throwable): OldResource<T> {
        val error: ErrorResponse
        if (t is IOException) {
            error = ErrorResponse(
                NETWORK_ERROR_CODE,
                CONNECTION_MSG_ERROR
            )
        } else {
            error = ErrorResponse(
                GENERIC_ERROR_CODE,
                GENERIC_MSG_ERROR_MESSAGE
            )
        }
        Log.e(logTag, t.message)
        return OldResource.error(error)
    }
}
