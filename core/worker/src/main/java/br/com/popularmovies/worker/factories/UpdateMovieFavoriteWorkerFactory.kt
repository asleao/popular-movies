package br.com.popularmovies.worker.factories

import android.content.Context
import androidx.work.WorkerParameters
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import br.com.popularmovies.worker.UpdateMovieFavoriteWorker
import br.com.popularmovies.worker.api.ChildWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

class UpdateMovieFavoriteWorkerFactoryImpl @Inject constructor(
    private val appContext: Provider<Context>,
    private val mMovieLocalDataSource: MovieLocalDataSource,
    private val mMovieRemoteDataSource: MovieRemoteDataSource,
) : ChildWorkerFactory<UpdateMovieFavoriteWorker> {

    override fun create(params: WorkerParameters): UpdateMovieFavoriteWorker {
        return UpdateMovieFavoriteWorker(
            appContext.get(),
            params,
            mMovieLocalDataSource,
            mMovieRemoteDataSource
        )
    }
}