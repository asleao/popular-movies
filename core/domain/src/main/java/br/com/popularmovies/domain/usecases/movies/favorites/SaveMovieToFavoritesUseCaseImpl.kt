package br.com.popularmovies.domain.usecases.movies.favorites

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.SaveMovieToFavoritesUseCase
import br.com.popularmovies.domain.api.usecases.SaveMovieToFavoritesUseCaseParams
import javax.inject.Inject

class SaveMovieToFavoritesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : SaveMovieToFavoritesUseCase {

    override suspend fun build(params: SaveMovieToFavoritesUseCaseParams): Result<Unit> {
        return movieRepository.saveToFavorites(params.movie)
    }
}