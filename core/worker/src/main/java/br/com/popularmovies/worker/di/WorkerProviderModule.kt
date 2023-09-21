package br.com.popularmovies.worker.di

import androidx.work.Configuration
import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WorkerProviderModule {

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(workerFactory: WorkerFactory): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }
}
