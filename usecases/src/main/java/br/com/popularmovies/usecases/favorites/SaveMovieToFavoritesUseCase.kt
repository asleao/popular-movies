package br.com.popularmovies.usecases.favorites

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.UseCase
import javax.inject.Inject

class SaveMovieToFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<SaveMovieToFavoritesUseCase.Params, Unit>() {

    override suspend fun build(param: Params) {
        return movieRepository.saveToFavorites(param.movie)
    }

    data class Params(
        val movie: Movie
    )
}