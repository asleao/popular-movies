package br.com.popularmovies.core.api

interface DatabaseComponentProvider {
    val remoteKeyLocalDataSource: RemoteKeyLocalDataSource
    val movieLocalDataSource: MovieLocalDataSource
}