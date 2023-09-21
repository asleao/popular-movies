package br.com.popularmovies.domain.usecases.movies.favorites

import androidx.work.WorkManager
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCaseParams
import br.com.popularmovies.worker.api.UpdateMovieFavoriteWorkerRequest
import javax.inject.Inject

class UpdateMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val workManager: WorkManager,
    private val updateMovieFavoriteWorkerRequest: UpdateMovieFavoriteWorkerRequest
) : UpdateMovieFavoriteUseCase {
    override suspend fun build(param: UpdateMovieFavoriteUseCaseParams): Unit {
        workManager.enqueue(
            updateMovieFavoriteWorkerRequest.request(
                param.movie.id,
                param.isFavorite
            )
        )
        return movieRepository.saveToFavorites(param.movie.id, param.isFavorite)
    }
}