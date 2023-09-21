package br.com.popularmovies.worker.requests

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import br.com.popularmovies.worker.UpdateMovieFavoriteWorker
import br.com.popularmovies.worker.api.UpdateMovieFavoriteWorkerRequest
import javax.inject.Inject

class UpdateMovieFavoriteWorkerRequestImpl @Inject constructor() :
    UpdateMovieFavoriteWorkerRequest {
    override fun request(movieId: Long, isFavorite: Boolean): OneTimeWorkRequest {
        val inputData = Data.Builder()
            .putLong(UpdateMovieFavoriteWorker.UPDATE_MOVIE_FAVORITE_ID, movieId)
            .putBoolean(UpdateMovieFavoriteWorker.UPDATE_MOVIE_FAVORITE_IS_FAVORITE, isFavorite)
            .build()
        return OneTimeWorkRequestBuilder<UpdateMovieFavoriteWorker>()
            .setInputData(inputData)
            .build()
    }
}