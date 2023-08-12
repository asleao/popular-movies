package br.com.popularmovies.datasourcedb.di

import br.com.popularmovies.datasourcedb.datasources.keys.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSource

interface DatabaseComponentProvider {
    val remoteKeyLocalDataSource: RemoteKeyLocalDataSource
    val movieLocalDataSource: MovieLocalDataSource
}