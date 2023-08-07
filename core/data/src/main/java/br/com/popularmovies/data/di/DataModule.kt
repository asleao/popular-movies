package br.com.popularmovies.data.di

import br.com.popularmovies.datasourcedb.datasources.keys.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSource
import br.com.popularmovies.datasourcedb.di.DatabaseModule
import br.com.popularmovies.datasourceremote.di.NetworkModule
import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.data.movie.MovieRepositoryImpl
import br.com.popularmovies.data.movie.MovieRepository
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
object DataModule {

    @Provides
    fun movieRepository(
        remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            remoteKeyLocalDataSource,
            movieLocalDataSource,
            movieRemoteDataSource
        )
    }
}
