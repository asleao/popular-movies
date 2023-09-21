package br.com.popularmovies.worker.di

import androidx.work.WorkerFactory
import br.com.popularmovies.worker.factories.CustomWorkerFactoryImpl
import dagger.Binds
import dagger.Module

@Module
interface WorkerFactoryBindingModule {

    @Binds
    fun bindsWorkerFactory(customWorkerFactoryImpl: CustomWorkerFactoryImpl): WorkerFactory
}
