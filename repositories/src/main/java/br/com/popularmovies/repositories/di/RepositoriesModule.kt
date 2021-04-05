package br.com.popularmovies.repositories.di

import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSource
import br.com.popularmovies.datasourcedb.di.DatabaseModule
import br.com.popularmovies.datasourceremote.di.NetworkModule
import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.repositories.movie.MovieRepository
import br.com.popularmovies.repositories.movie.MovieRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [
    NetworkModule::class,
    DatabaseModule::class
])
object RepositoriesModule {

    @Provides
    fun movieRepository(movieRemoteDataSource: MovieRemoteDataSource,
                        movieLocalDataSource: MovieLocalDataSource): MovieRepository {
        return MovieRepositoryImpl(movieLocalDataSource, movieRemoteDataSource)
    }
}
