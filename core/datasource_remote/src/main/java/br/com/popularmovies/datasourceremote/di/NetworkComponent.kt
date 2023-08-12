package br.com.popularmovies.datasourceremote.di

import dagger.Component

//@Singleton
@Component(
    modules = [NetworkModule::class, NetworkRepositoryModule::class]
)
//@Singleton
interface NetworkComponent : NetworkComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }
}
