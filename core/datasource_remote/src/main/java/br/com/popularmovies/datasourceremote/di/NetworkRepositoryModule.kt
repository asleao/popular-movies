package br.com.popularmovies.datasourceremote.di

import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface NetworkRepositoryModule {

    @Binds
//    @Singleton
    fun bindsMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}
