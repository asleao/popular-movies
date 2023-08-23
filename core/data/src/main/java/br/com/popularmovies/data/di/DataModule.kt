package br.com.popularmovies.data.di

import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.data.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {
    @Binds
    fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}