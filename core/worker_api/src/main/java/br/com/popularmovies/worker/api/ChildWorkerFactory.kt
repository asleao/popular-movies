package br.com.popularmovies.worker.api

import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory<T : ListenableWorker> {
    fun create(params: WorkerParameters): T
}