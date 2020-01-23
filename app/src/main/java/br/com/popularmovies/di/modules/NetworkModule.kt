package br.com.popularmovies.di.modules

import android.content.Context
import androidx.room.Room
import br.com.popularmovies.BuildConfig
import br.com.popularmovies.core.network.API_VERSION
import br.com.popularmovies.core.network.HOST
import br.com.popularmovies.core.network.SCHEME
import br.com.popularmovies.core.network.local.AppDatabase
import br.com.popularmovies.core.network.retrofit.interceptor.AuthorizationInterceptor
import br.com.popularmovies.di.qualifiers.MoviesLocalDataSource
import br.com.popularmovies.di.qualifiers.MoviesRemoteDataSource
import br.com.popularmovies.services.movieService.source.MovieDataSource
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource
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
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "popularmovies"
        ).build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
                .add(BigDecimalAdapter())
                .add(DateAdapter())
                .build()
    }

    @Provides
    @Singleton
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
    @Singleton
    fun providesOhHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder.build()

    }

    @Provides
    @Singleton
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
    @Singleton
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
