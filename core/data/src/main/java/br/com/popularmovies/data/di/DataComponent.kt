package br.com.popularmovies.data.di

import br.com.popularmovies.datasourcedb.di.DatabaseComponent
import br.com.popularmovies.datasourceremote.di.NetworkComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        DatabaseComponent::class,
        NetworkComponent::class
    ],
    modules = [DataModule::class]
)
//@Singleton
interface DataComponent : DataComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(
            databaseComponent: DatabaseComponent,
            networkComponent: NetworkComponent
        ): DataComponent
    }
}
