package br.com.popularmovies.worker.di

import android.content.Context
import br.com.popularmovies.core.api.DatabaseComponentProvider
import br.com.popularmovies.datasourceremoteapi.NetworkComponentProvider
import br.com.popularmovies.worker.api.WorkerComponentProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        DatabaseComponentProvider::class,
        NetworkComponentProvider::class
    ],
    modules = [
        WorkerBindingModule::class,
        WorkerFactoryBindingModule::class,
        WorkerProviderModule::class,
        WorkerRequestBindingModule::class
    ]
)
@Singleton
interface WorkerComponent : WorkerComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            databaseComponentProvider: DatabaseComponentProvider,
            networkComponentProvider: NetworkComponentProvider
        ): WorkerComponent
    }
}
