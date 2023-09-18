package br.com.popularmovies.domain.usecases.movies.favorites

import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCaseParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : UpdateMovieFavoriteUseCase {
    override fun build(param: UpdateMovieFavoriteUseCaseParams): Flow<Unit> {
        return movieRepository.saveToFavorites(param.movie, param.isFavorite)
    }
}