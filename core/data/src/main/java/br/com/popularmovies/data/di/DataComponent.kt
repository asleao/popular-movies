package br.com.popularmovies.data.di

import br.com.popularmovies.datasourcedb.di.DatabaseComponent
import br.com.popularmovies.datasourceremoteapi.NetworkComponentProvider
import dagger.Component

@Component(
    dependencies = [
        DatabaseComponent::class,
        NetworkComponentProvider::class
    ],
    modules = [DataModule::class]
)
//@Singleton
interface DataComponent : DataComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(
            databaseComponent: DatabaseComponent,
            networkComponentProvider: NetworkComponentProvider
        ): DataComponent
    }
}
