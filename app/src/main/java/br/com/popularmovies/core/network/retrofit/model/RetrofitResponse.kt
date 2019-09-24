package br.com.popularmovies.core.network.retrofit.model

import br.com.popularmovies.core.network.*
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.EOFException
import java.io.IOException

class RetrofitResponse<T>(private val request: suspend () -> Response<T>) :
        ApiResponse<T> {

    override suspend fun result(): Resource<T> {
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

    override fun success(data: T): Resource<T> {
        return Resource.success(data)
    }

    override fun error(code: Int, errorBody: ResponseBody?): Resource<T> {
        val error = if (code == CODE_ERRO_NEGOCIO && errorBody != null) {
            businessLogicError(errorBody)
        } else {
            genericError()
        }
        return Resource.error(error)
    }

    override fun failure(exception: Exception): Resource<T> {
        return if (exception is IOException && exception !is EOFException) {
            Resource.error(connectionError())
        } else {
            Resource.error(genericError())
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
                codErro = CODE_ERRO_PADRAO,
                title = TITULO_MSG_ERRO_CONEXAO,
                message = MSG_ERRO_CONEXAO
        )
    }

    private fun genericError(): Error {
        return Error(
                codErro = CODE_ERRO_PADRAO,
                title = TITULO_MSG_ERRO_PADRAO,
                message = MSG_ERRO_PADRAO
        )
    }
}