package br.com.popularmovies.core.network.retrofit.model

import br.com.popularmovies.core.network.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.IOException

internal class RetrofitResponseTest {
    val response = mockk<Response<Boolean>>()

    @Nested
    inner class Result {
        @Test
        fun `ao obter sucesso, com o body preenchido, em uma request, result deve retornar um success`() {
            every { response.isSuccessful } answers { true }
            every { response.body() } answers { true }

            val retrofitResponse = runBlocking {
                RetrofitResponse { response }
                        .result()
            }

            assertThat(retrofitResponse.data).isTrue()
        }

        @Test
        fun `ao obter sucesso, com o body nulo, em uma request, result deve retornar um genericError`() {
            every { response.isSuccessful } answers { true }
            every { response.body() } answers { null }

            val retrofitResponse = runBlocking {
                RetrofitResponse { response }
                        .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(CODE_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.title).isEqualTo(TITULO_MSG_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.message).isEqualTo(MSG_ERRO_PADRAO)
        }

        @Test
        fun `ao obter erro de negocio com o body vazio em uma request, result deve retornar um genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { CODE_ERRO_NEGOCIO }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                "{}".toResponseBody("application/json".toMediaType())
            }
            val retrofitResponse = runBlocking { RetrofitResponse { response }.result() }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(CODE_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.title).isEqualTo(TITULO_MSG_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.message).isEqualTo(MSG_ERRO_PADRAO)
        }

        @Test
        fun `ao obter erro de negocio com o body nulo em uma request, result deve retornar um genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { CODE_ERRO_NEGOCIO }
            every { response.body() } answers { null }
            every { response.errorBody() } answers { null }

            val retrofitResponse = runBlocking {
                RetrofitResponse { response }
                        .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(CODE_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.title).isEqualTo(TITULO_MSG_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.message).isEqualTo(MSG_ERRO_PADRAO)
        }

        @Test
        fun `ao obter erro de negocio com o body preenchido em uma request, result deve retornar um businessLogicError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { CODE_ERRO_NEGOCIO }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                val content = "{\n" +
                        "   \"codErro\":5,\n" +
                        "   \"title\":\"Ocorreu um Erro\",\n" +
                        "   \"message\":\"Erro de negocio\"\n" +
                        "}"
                content.toResponseBody("application/json".toMediaType())
            }

            val retrofitResponse = runBlocking {
                RetrofitResponse { response }
                        .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(5)
            assertThat(retrofitResponse.error?.title).isEqualTo("Ocorreu um Erro")
            assertThat(retrofitResponse.error?.message).isEqualTo("Erro de negocio")
        }

        @Test
        fun `ao obter erro com a resposta em plain text, o result deve retornar um onFailure com um genericError`() {
            every { response.isSuccessful } answers { false }
            every { response.code() } answers { CODE_ERRO_NEGOCIO }
            every { response.body() } answers { null }
            every { response.errorBody() } answers {
                "".toResponseBody("application/json".toMediaType())
            }

            val retrofitResponse = runBlocking {
                RetrofitResponse { response }
                        .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(CODE_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.title).isEqualTo(TITULO_MSG_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.message).isEqualTo(MSG_ERRO_PADRAO)
        }

        @Test
        fun `quando o celular estiver sem conexao, o result deve retornar um onFailure com um connectionError`() {
            //TODO verificar como lançar o throws ao chamar o invoke()
            every { response.body() } throws IOException()

            val retrofitResponse = runBlocking {
                RetrofitResponse { response }
                        .result()
            }

            assertThat(retrofitResponse.error).isNotNull
            assertThat(retrofitResponse.error?.codErro).isEqualTo(CODE_ERRO_PADRAO)
            assertThat(retrofitResponse.error?.title).isEqualTo(TITULO_MSG_ERRO_CONEXAO)
            assertThat(retrofitResponse.error?.message).isEqualTo(MSG_ERRO_CONEXAO)
        }
    }
}