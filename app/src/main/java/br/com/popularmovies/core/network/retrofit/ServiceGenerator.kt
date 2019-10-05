package br.com.popularmovies.core.network.retrofit

import br.com.popularmovies.BuildConfig
import br.com.popularmovies.core.network.API_VERSION
import br.com.popularmovies.core.network.HOST
import br.com.popularmovies.core.network.SCHEME
import br.com.popularmovies.core.network.retrofit.interceptor.AuthorizationInterceptor
import br.com.popularmovies.utils.BigDecimalAdapter
import br.com.popularmovies.utils.DateAdapter
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceGenerator {

    private val moshiFactory = Moshi.Builder()
        .add(BigDecimalAdapter())
        .add(DateAdapter())
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(buildUrl())
        .addConverterFactory(MoshiConverterFactory.create(moshiFactory))

    private var retrofit = builder.build()

    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor())

    fun <S> createService(
        serviceClass: Class<S>
    ): S {
        if (!httpClient.interceptors().contains(
                logging
            ) && BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(StethoInterceptor())
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }

        return retrofit.create(serviceClass)
    }

    private fun buildUrl(): HttpUrl {
        return HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .addPathSegments(API_VERSION)
            .build()
    }
}
