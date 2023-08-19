package br.com.popularmovies.datasourceremote.di

import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSourceImpl
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import dagger.Binds
import dagger.Module

@Module
interface NetworkRepositoryModule {

    @Binds
//    @Singleton
    fun bindsMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}
