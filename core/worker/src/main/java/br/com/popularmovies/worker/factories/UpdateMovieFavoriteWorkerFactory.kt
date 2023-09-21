package br.com.popularmovies.worker.factories

import android.content.Context
import androidx.work.WorkerParameters
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.worker.UpdateMovieFavoriteWorker
import br.com.popularmovies.worker.api.ChildWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

class UpdateMovieFavoriteWorkerFactoryImpl @Inject constructor(
    private val appContext: Provider<Context>,
    private val movieRepository: Provider<MovieRepository>
) : ChildWorkerFactory<UpdateMovieFavoriteWorker> {

    override fun create(params: WorkerParameters): UpdateMovieFavoriteWorker {
        return UpdateMovieFavoriteWorker(
            appContext.get(),
            params,
            movieRepository.get()
        )
    }
}