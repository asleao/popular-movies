package br.com.popularmovies.datasourceremote.di

import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource

interface NetworkComponentProvider {
    val movieRemoteDataSource: MovieRemoteDataSource
}