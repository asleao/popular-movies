package br.com.popularmovies.data.di

import br.com.popularmovies.data.movie.MovieRepository
import br.com.popularmovies.data.movie.MovieRepositoryImpl
import br.com.popularmovies.datasourcedb.di.DatabaseModule
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DatabaseModule::class
    ]
)
interface DataModule {
    @Binds
//    @Singleton
    fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
