package br.com.popularmovies.datasourceremoteapi

interface NetworkComponentProvider {
    val movieRemoteDataSource: MovieRemoteDataSource
}