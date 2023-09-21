package br.com.popularmovies.worker.api

import androidx.work.Configuration

interface WorkerComponentProvider {
    val configuration: Configuration
}