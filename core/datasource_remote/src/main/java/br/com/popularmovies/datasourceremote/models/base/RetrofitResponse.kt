package br.com.popularmovies.datasourceremote.models.base

import br.com.popularmovies.common.configs.ErrorCodes.BUSINESS_LOGIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorCodes.GENERIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorCodes.NETWORK_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.config.NetworkConstants.NETWORK_ERROR_MSG
import br.com.popularmovies.datasourceremote.config.NetworkConstants.NETWORK_ERROR_TITLE
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.EOFException
import java.io.IOException
import javax.inject.Inject

class RetrofitResponse @Inject constructor(val moshi: Moshi) : ApiResponse {

    override suspend fun <T> request(response: suspend () -> Response<T>): Result<T> {
        return try {
            with(response.invoke()) {
                val data = body()
                if (isSuccessful && data != null) {
                    success(data)
                } else {
                    error(code(), errorBody())
                }
            }
        } catch (exception: Exception) {
            failure(exception)
        }
    }

    override fun <T> success(data: T): Result.Success<T> {
        return Result.Success(data)
    }

    override fun <T> error(code: Int, errorBody: ResponseBody?): Result.Error<T> {
        val error = if (code == BUSINESS_LOGIC_ERROR_CODE && errorBody != null) {
            businessLogicError(errorBody)
        } else {
            genericError()
        }
        return Result.Error(error)
    }

    override fun <T> failure(exception: Exception): Result.Error<T> {
        return if (exception is IOException && exception !is EOFException) {
            Result.Error(connectionError())
        } else {
            Result.Error(genericError())
        }
    }

    private fun businessLogicError(errorBody: ResponseBody): NetworkError {
        val jsonAdapter = moshi.adapter(NetworkError::class.java)
        val errorObject = jsonAdapter.fromJson(errorBody.string())
        return if (errorObject == null || !errorObject.isValid()) {
            genericError()
        } else {
            NetworkError(
                code = errorObject.code,
                title = errorObject.title,
                message = errorObject.message
            )
        }
    }

    private fun connectionError(): NetworkError {
        return NetworkError(
            code = NETWORK_ERROR_CODE,
            title = NETWORK_ERROR_TITLE,
            message = NETWORK_ERROR_MSG
        )
    }

    private fun genericError(): NetworkError {
        return NetworkError(
            code = GENERIC_ERROR_CODE,
            title = GENERIC_MSG_ERROR_TITLE,
            message = GENERIC_MSG_ERROR_MESSAGE
        )
    }
}