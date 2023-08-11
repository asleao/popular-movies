package br.com.popularmovies.datasourceremote.di

import br.com.popularmovies.core.datasourceremote.BuildConfig
import br.com.popularmovies.datasourceremote.adapters.BigDecimalAdapter
import br.com.popularmovies.datasourceremote.adapters.DateAdapter
import br.com.popularmovies.datasourceremote.config.NetworkConstants.API_VERSION
import br.com.popularmovies.datasourceremote.config.NetworkConstants.HOST
import br.com.popularmovies.datasourceremote.config.NetworkConstants.SCHEME
import br.com.popularmovies.datasourceremote.interceptors.AuthorizationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
//    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(BigDecimalAdapter())
            .add(DateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
//    @Singleton
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
//    @Singleton
    fun providesOhHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder.build()

    }

    @Provides
//    @Singleton
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
//    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: HttpUrl,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }
}
