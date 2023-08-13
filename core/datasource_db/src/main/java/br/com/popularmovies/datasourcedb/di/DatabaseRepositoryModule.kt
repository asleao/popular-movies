package br.com.popularmovies.datasourcedb.di

import br.com.popularmovies.core.api.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.keys.RemoteKeyLocalDataSourceImpl
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DatabaseRepositoryModule {
    @Binds
//    @Singleton
    fun bindsRemoteKeyLocalDataSource(remoteKeyLocalDataSource: RemoteKeyLocalDataSourceImpl): RemoteKeyLocalDataSource

    @Binds
//    @Singleton
    fun bindsMovieLocalDataSource(movieLocalDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource
}