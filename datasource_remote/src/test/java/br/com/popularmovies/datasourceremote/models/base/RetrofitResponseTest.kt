package br.com.popularmovies.datasourceremote.models.base

import br.com.popularmovies.common.configs.ErrorCodes.BUSINESS_LOGIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorCodes.GENERIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorCodes.NETWORK_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.common.models.base.Result.Error
import br.com.popularmovies.common.models.base.Result.Success
import br.com.popularmovies.datasourceremote.config.NetworkConstants.NETWORK_ERROR_MSG
import br.com.popularmovies.datasourceremote.config.NetworkConstants.NETWORK_ERROR_TITLE
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.IOException

internal class RetrofitResponseTest {
    val response =
        mockk<Response<Boolean>>()
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val retrofitResponse = RetrofitResponse(moshi)


    @AfterEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class Result {
        @Test
        fun `when isSuccessful with a body in a request, then result should return a Success with data`() {
            every { response.isSuccessful } answers { true }
            every { response.body() } answers { true }

            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Success<Boolean>

            assertThat(result.data).isTrue
        }

        @Test
        fun `when isSuccessful with a null body in a request, then result should return a genericError`() {
            every { response.isSuccessful } answers { true }
            every { response.body() } answers { null }

            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Error<Boolean>

            assertThat(result.error).isNotNull
            assertThat(result.error.code).isEqualTo(GENERIC_ERROR_CODE)
            assertThat(result.error.title).isEqualTo(GENERIC_MSG_ERROR_TITLE)
            assertThat(result.error.message).isEqualTo(GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when is a business logic error with a empty body in a request, then result should return a genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                "{}".toResponseBody("application/json".toMediaType())
            }
            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Error<Boolean>

            assertThat(result.error).isNotNull
            assertThat(result.error.code).isEqualTo(GENERIC_ERROR_CODE)
            assertThat(result.error.title).isEqualTo(GENERIC_MSG_ERROR_TITLE)
            assertThat(result.error.message).isEqualTo(GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when is a business logic error with a null body in a request, then result should return a genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers { null }

            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Error<Boolean>

            assertThat(result.error).isNotNull
            assertThat(result.error.code).isEqualTo(GENERIC_ERROR_CODE)
            assertThat(result.error.title).isEqualTo(GENERIC_MSG_ERROR_TITLE)
            assertThat(result.error.message).isEqualTo(GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when is a business logic error with a body in a request, then result should return a businessLogicError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                val content = "{\n" +
                        "   \"code\":5,\n" +
                        "   \"title\":\"Error\",\n" +
                        "   \"message\":\"Something went wrong\"\n" +
                        "}"
                content.toResponseBody("application/json".toMediaType())
            }

            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Error<Boolean>

            assertThat(result.error).isNotNull
            assertThat(result.error.code).isEqualTo(5)
            assertThat(result.error.title).isEqualTo("Error")
            assertThat(result.error.message).isEqualTo("Something went wrong")
        }

        @Test
        fun `when is a generic error with a response in plain text, then result should return onFailure with a genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                "".toResponseBody("application/json".toMediaType())
            }

            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Error<Boolean>

            assertThat(result.error).isNotNull
            assertThat(result.error.code).isEqualTo(GENERIC_ERROR_CODE)
            assertThat(result.error.title).isEqualTo(GENERIC_MSG_ERROR_TITLE)
            assertThat(result.error.message).isEqualTo(GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when there is no connection, then result should return onFailure with a connectionError`() {
            every { response.body() } throws IOException()

            val request = runBlocking { retrofitResponse.request { response } }

            val result = request.mapApiResult() as Error<Boolean>

            assertThat(result.error).isNotNull
            assertThat(result.error.code).isEqualTo(NETWORK_ERROR_CODE)
            assertThat(result.error.title).isEqualTo(NETWORK_ERROR_TITLE)
            assertThat(result.error.message).isEqualTo(NETWORK_ERROR_MSG)
        }
    }
}