package br.com.popularmovies.datasourcedb.di

import android.content.Context
import br.com.popularmovies.datasourcedb.datasources.keys.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSource
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DatabaseModule::class, DatabaseRepositoryModule::class])
interface DatabaseComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DatabaseComponent
    }

    val remoteKeyLocalDataSource: RemoteKeyLocalDataSource
    val movieLocalDataSource: MovieLocalDataSource
}
