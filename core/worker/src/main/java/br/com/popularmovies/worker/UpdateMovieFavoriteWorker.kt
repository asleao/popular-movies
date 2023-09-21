package br.com.popularmovies.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMovieFavoriteWorker @Inject constructor(
    context: Context,
    private val params: WorkerParameters,
    private val mMovieLocalDataSource: MovieLocalDataSource,
    private val mMovieRemoteDataSource: MovieRemoteDataSource
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val movieId = params.inputData.getLong(UPDATE_MOVIE_FAVORITE_ID, -1L)
            val isFavorite = params.inputData.getBoolean(UPDATE_MOVIE_FAVORITE_IS_FAVORITE, false)
            if (movieId != -1L) {
                //TODO Implement
                Result.success()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val UPDATE_MOVIE_FAVORITE_ID = "update_movie_favorite_id"
        const val UPDATE_MOVIE_FAVORITE_IS_FAVORITE = "update_movie_favorite_is_favorite"
    }
}