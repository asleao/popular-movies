package br.com.popularmovies.usecases.movies.favorites

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.repository.MovieRepository
import br.com.popularmovies.usecases.UseCase
import javax.inject.Inject

class SaveMovieToFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<SaveMovieToFavoritesUseCase.Params, Unit>() {

    override suspend fun build(param: Params): Result<Unit> {
        return movieRepository.saveToFavorites(param.movie)
    }

    data class Params(
        val movie: Movie
    )
}