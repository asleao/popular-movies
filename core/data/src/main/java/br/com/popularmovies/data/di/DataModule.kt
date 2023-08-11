package br.com.popularmovies.data.di

import br.com.popularmovies.data.movie.MovieRepository
import br.com.popularmovies.data.movie.MovieRepositoryImpl
import br.com.popularmovies.datasourcedb.di.DatabaseModule
import br.com.popularmovies.datasourceremote.di.NetworkModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface DataModule {
    @Binds
//    @Singleton
    fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
