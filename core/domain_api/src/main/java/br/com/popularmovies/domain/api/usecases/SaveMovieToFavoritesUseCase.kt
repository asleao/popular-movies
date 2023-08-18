package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.UseCase
import br.com.popularmovies.model.movie.Movie

interface SaveMovieToFavoritesUseCase : UseCase<SaveMovieToFavoritesUseCaseParams, Unit>

data class SaveMovieToFavoritesUseCaseParams(
    val movie: Movie
)
