package br.com.popularmovies.data.di

import br.com.popularmovies.core.api.DatabaseComponentProvider
import br.com.popularmovies.core.data.api.DataComponentProvider
import br.com.popularmovies.datasourceremoteapi.NetworkComponentProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        DatabaseComponentProvider::class,
        NetworkComponentProvider::class
    ],
    modules = [DataModule::class]
)
@Singleton
interface DataComponent : DataComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(
            databaseComponentProvider: DatabaseComponentProvider,
            networkComponentProvider: NetworkComponentProvider
        ): DataComponent
    }
}
