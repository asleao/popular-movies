package br.com.popularmovies.worker.api

import androidx.work.Configuration
import androidx.work.WorkManager

interface WorkerComponentProvider {
    val configuration: Configuration
    val workManager: WorkManager
    val updateMovieFavoriteWorkerRequest: UpdateMovieFavoriteWorkerRequest
}