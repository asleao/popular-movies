package br.com.popularmovies.datasourceremote.di

import br.com.popularmovies.datasourceremoteapi.NetworkComponentProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [NetworkModule::class, NetworkRepositoryModule::class]
)
@Singleton
interface NetworkComponent : NetworkComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }
}
