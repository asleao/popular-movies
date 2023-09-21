package br.com.popularmovies.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.popularmovies.core.data.api.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateMovieFavoriteWorker @Inject constructor(
    context: Context,
    val params: WorkerParameters,
    val movieRepository: MovieRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val movieId = params.inputData.getLong(UPDATE_MOVIE_FAVORITE_ID, -1L)
            val isFavorite = params.inputData.getBoolean(UPDATE_MOVIE_FAVORITE_IS_FAVORITE, false)
            if (movieId != -1L) {
                movieRepository.saveToFavorites(movieId, isFavorite)
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