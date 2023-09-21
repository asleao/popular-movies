package br.com.popularmovies.worker.api

import androidx.work.WorkerFactory

interface WorkerComponentProvider {
    val customWorkerFactory: WorkerFactory
}