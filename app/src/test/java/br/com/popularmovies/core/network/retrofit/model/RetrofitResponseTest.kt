package br.com.popularmovies.core.network.retrofit.model

import br.com.popularmovies.datanetwork.config.BUSINESS_LOGIC_ERROR_CODE
import br.com.popularmovies.datanetwork.config.NETWORK_ERROR_TITLE
import br.com.popularmovies.datanetwork.config.NETWORK_ERROR_MSG
import br.com.popularmovies.datanetwork.config.GENERIC_ERROR_CODE
import br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.datanetwork.config.NETWORK_ERROR_CODE
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
    val response = mockk<Response<Boolean>>()

    @AfterEach
    fun init(){
        clearAllMocks()
    }

    @Nested
    inner class Result {
        @Test
        fun `when isSuccessful with a body in a request, then result should return a resource with data`() {
            every { response.isSuccessful } answers { true }
            every { response.body() } answers { true }

            val retrofitResponse = runBlocking {
                br.com.popularmovies.datanetwork.config.RetrofitResponse { response }
                    .result()
            }

            assertThat(retrofitResponse.data).isTrue()
        }

        @Test
        fun `when isSuccessful with a null body in a request, then result should return a genericError`() {
            every { response.isSuccessful } answers { true }
            every { response.body() } answers { null }

            val retrofitResponse = runBlocking {
                br.com.popularmovies.datanetwork.config.RetrofitResponse { response }
                    .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_ERROR_CODE)
            assertThat(retrofitResponse.error?.title).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_TITLE)
            assertThat(retrofitResponse.error?.message).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when is a business logic error with a empty body in a request, then result should return a genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { br.com.popularmovies.datanetwork.config.BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                "{}".toResponseBody("application/json".toMediaType())
            }
            val retrofitResponse = runBlocking { br.com.popularmovies.datanetwork.config.RetrofitResponse { response }.result() }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_ERROR_CODE)
            assertThat(retrofitResponse.error?.title).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_TITLE)
            assertThat(retrofitResponse.error?.message).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when is a business logic error with a null body in a request, then result should return a genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { br.com.popularmovies.datanetwork.config.BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers { null }

            val retrofitResponse = runBlocking {
                br.com.popularmovies.datanetwork.config.RetrofitResponse { response }
                    .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_ERROR_CODE)
            assertThat(retrofitResponse.error?.title).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_TITLE)
            assertThat(retrofitResponse.error?.message).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when is a business logic error with a body in a request, then result should return a businessLogicError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { br.com.popularmovies.datanetwork.config.BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                val content = "{\n" +
                    "   \"codErro\":5,\n" +
                    "   \"title\":\"Error\",\n" +
                    "   \"message\":\"Something went wrong\"\n" +
                    "}"
                content.toResponseBody("application/json".toMediaType())
            }

            val retrofitResponse = runBlocking {
                br.com.popularmovies.datanetwork.config.RetrofitResponse { response }
                    .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(5)
            assertThat(retrofitResponse.error?.title).isEqualTo("Error")
            assertThat(retrofitResponse.error?.message).isEqualTo("Something went wrong")
        }

        @Test
        fun `when is a generic error with a response in plain text, then result should return onFailure with a genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { br.com.popularmovies.datanetwork.config.BUSINESS_LOGIC_ERROR_CODE }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                "".toResponseBody("application/json".toMediaType())
            }

            val retrofitResponse = runBlocking {
                br.com.popularmovies.datanetwork.config.RetrofitResponse { response }
                    .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_ERROR_CODE)
            assertThat(retrofitResponse.error?.title).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_TITLE)
            assertThat(retrofitResponse.error?.message).isEqualTo(br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_MESSAGE)
        }

        @Test
        fun `when there is no connection, then result should return onFailure with a connectionError`() {
            //TODO verificar como lançar o throws ao chamar o invoke()
            every { response.body() } throws IOException()

            val retrofitResponse = runBlocking {
                br.com.popularmovies.datanetwork.config.RetrofitResponse { response }
                    .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(br.com.popularmovies.datanetwork.config.NETWORK_ERROR_CODE)
            assertThat(retrofitResponse.error?.title).isEqualTo(br.com.popularmovies.datanetwork.config.NETWORK_ERROR_TITLE)
            assertThat(retrofitResponse.error?.message).isEqualTo(br.com.popularmovies.datanetwork.config.NETWORK_ERROR_MSG)
        }
    }
}