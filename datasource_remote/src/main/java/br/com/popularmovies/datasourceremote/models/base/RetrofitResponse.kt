package br.com.popularmovies.datasourceremote.models.base

import br.com.popularmovies.common.configs.ErrorCodes.BUSINESS_LOGIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorCodes.GENERIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorCodes.NETWORK_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.common.models.base.Error
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.config.NetworkConstants.NETWORK_ERROR_MSG
import br.com.popularmovies.datasourceremote.config.NetworkConstants.NETWORK_ERROR_TITLE
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.EOFException
import java.io.IOException

class RetrofitResponse<T>(private val request: suspend () -> Response<T>) :
        ApiResponse<T> {

    override suspend fun result(): Result<T> {
        return try {
            val response = request.invoke()
            val data = response.body()
            if (response.isSuccessful && data != null) {
                success(data)
            } else {
                error(response.code(), response.errorBody())
            }
        } catch (exception: Exception) {
            failure(exception)
        }
    }

    override fun success(data: T): Result.Success<T> {
        return Result.Success(data)
    }

    override fun error(code: Int, errorBody: ResponseBody?): Result.Error<T> {
        val error = if (code == BUSINESS_LOGIC_ERROR_CODE && errorBody != null) {
            businessLogicError(errorBody)
        } else {
            genericError()
        }
        return Result.Error(error)
    }

    override fun failure(exception: Exception): Result.Error<T> {
        return if (exception is IOException && exception !is EOFException) {
            Result.Error(connectionError())
        } else {
            Result.Error(genericError())
        }
    }

    private fun businessLogicError(errorBody: ResponseBody): Error {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(Error::class.java)
        val errorObject = jsonAdapter.fromJson(errorBody.string())
        return if (errorObject == null || !errorObject.isValid()) {
            genericError()
        } else {
            Error(
                    codErro = errorObject.codErro,
                    title = errorObject.title,
                    message = errorObject.message
            )
        }
    }

    private fun connectionError(): Error {
        return Error(
                codErro = NETWORK_ERROR_CODE,
                title = NETWORK_ERROR_TITLE,
                message = NETWORK_ERROR_MSG
        )
    }

    private fun genericError(): Error {
        return Error(
                codErro = GENERIC_ERROR_CODE,
                title = GENERIC_MSG_ERROR_TITLE,
                message = GENERIC_MSG_ERROR_MESSAGE
        )
    }
}