package br.com.popularmovies.di

import br.com.popularmovies.BuildConfig
import br.com.popularmovies.core.network.API_VERSION
import br.com.popularmovies.core.network.HOST
import br.com.popularmovies.core.network.SCHEME
import br.com.popularmovies.core.network.retrofit.interceptor.AuthorizationInterceptor
import br.com.popularmovies.utils.BigDecimalAdapter
import br.com.popularmovies.utils.DateAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
                .add(BigDecimalAdapter())
                .add(DateAdapter())
                .build()
    }

    @Provides
    fun providesOkHttpClientBuilder(log: HttpLoggingInterceptor): OkHttpClient.Builder {
        val interceptors = OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
        return if (BuildConfig.DEBUG) {
            interceptors
                    .addInterceptor(log)
        } else {
            interceptors
        }
    }

    @Provides
    fun providesOhHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder.build()

    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesBaseUrl(): HttpUrl {
        return HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(API_VERSION)
                .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient,
                         baseUrl: HttpUrl,
                         moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory
                        .create(moshi))
                .client(okHttpClient)
                .build()

    }
}