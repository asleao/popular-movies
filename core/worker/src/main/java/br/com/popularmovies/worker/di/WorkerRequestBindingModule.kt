package br.com.popularmovies.worker.di

import br.com.popularmovies.worker.api.UpdateMovieFavoriteWorkerRequest
import br.com.popularmovies.worker.requests.UpdateMovieFavoriteWorkerRequestImpl
import dagger.Binds
import dagger.Module

@Module
interface WorkerRequestBindingModule {

    @Binds
    fun bindsUpdateMovieFavoriteWorkerRequest(updateMovieFavoriteWorkerRequestImpl: UpdateMovieFavoriteWorkerRequestImpl): UpdateMovieFavoriteWorkerRequest
}
