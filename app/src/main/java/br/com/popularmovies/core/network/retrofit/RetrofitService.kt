package br.com.popularmovies.core.network.retrofit

import br.com.popularmovies.core.network.API_VERSION
import br.com.popularmovies.core.network.HOST
import br.com.popularmovies.core.network.SCHEME
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitService {


    fun <S> createService(serviceClass: Class<S>): S {

        val client = createHttpClient()

        return Retrofit.Builder()
                .baseUrl(buildUrl())
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(serviceClass)
    }

    private fun createHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    private fun buildUrl(): HttpUrl {
        return HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(API_VERSION)
                .build()
    }
}