package br.com.popularmovies.data.di

import br.com.popularmovies.data.movie.MovieRepository
import br.com.popularmovies.data.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
//    @Singleton
    fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
