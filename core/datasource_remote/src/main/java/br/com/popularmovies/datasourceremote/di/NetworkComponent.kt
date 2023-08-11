package br.com.popularmovies.datasourceremote.di

import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import dagger.Component

//@Singleton
@Component(modules = [NetworkModule::class, NetworkRepositoryModule::class])
interface NetworkComponent {
    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }

    val movieRemoteDataSource: MovieRemoteDataSource
}
