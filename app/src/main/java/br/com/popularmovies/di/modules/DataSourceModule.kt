package br.com.popularmovies.di.modules

import br.com.popularmovies.core.network.local.AppDatabase
import br.com.popularmovies.di.qualifiers.MoviesLocalDataSource
import br.com.popularmovies.di.qualifiers.MoviesRemoteDataSource
import br.com.popularmovies.services.movieService.source.MovieDataSource
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    @MoviesRemoteDataSource
    fun providesMovieRemoteDataSource(): MovieDataSource {
        return MovieRemoteDataSource()
    }

    @Provides
    @Singleton
    @MoviesLocalDataSource
    fun providesMovieLocalDataSource(appDatabase: AppDatabase): MovieDataSource {
        return MovieLocalDataSource(appDatabase)
    }
}