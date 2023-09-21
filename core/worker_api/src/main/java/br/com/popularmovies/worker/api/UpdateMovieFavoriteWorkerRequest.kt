package br.com.popularmovies.worker.api

import androidx.work.OneTimeWorkRequest

interface UpdateMovieFavoriteWorkerRequest {
    fun request(movieId: Long, isFavorite: Boolean): OneTimeWorkRequest
}