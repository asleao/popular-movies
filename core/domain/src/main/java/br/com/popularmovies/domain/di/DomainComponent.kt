package br.com.popularmovies.domain.di

import br.com.popularmovies.core.data.api.DataComponentProvider
import br.com.popularmovies.domain.api.DomainComponentProvider
import br.com.popularmovies.worker.api.WorkerComponentProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [
        DataComponentProvider::class,
        WorkerComponentProvider::class
    ],
    modules = [DomainModule::class]
)
@Singleton
interface DomainComponent : DomainComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(
            dataComponentProvider: DataComponentProvider,
            workerComponentProvider: WorkerComponentProvider
        ): DomainComponent
    }
}
