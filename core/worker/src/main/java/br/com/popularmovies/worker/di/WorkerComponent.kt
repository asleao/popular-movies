package br.com.popularmovies.worker.di

import android.content.Context
import br.com.popularmovies.core.data.api.DataComponentProvider
import br.com.popularmovies.worker.api.WorkerComponentProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [DataComponentProvider::class],
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
            dataComponentProvider: DataComponentProvider
        ): WorkerComponent
    }
}
