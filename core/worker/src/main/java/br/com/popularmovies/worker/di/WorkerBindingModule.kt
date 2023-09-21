package br.com.popularmovies.worker.di

import androidx.work.ListenableWorker
import br.com.popularmovies.common.di.WorkerKey
import br.com.popularmovies.worker.UpdateMovieFavoriteWorker
import br.com.popularmovies.worker.api.ChildWorkerFactory
import br.com.popularmovies.worker.factories.UpdateMovieFavoriteWorkerFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(UpdateMovieFavoriteWorker::class)
    fun bindUpdateMovieFavoriteWorkerFactory(updateMovieFavoriteWorkerFactory: UpdateMovieFavoriteWorkerFactoryImpl): ChildWorkerFactory<out ListenableWorker>
}
