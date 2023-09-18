package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.model.movie.Movie

interface UpdateMovieFavoriteUseCase : FlowUseCase<UpdateMovieFavoriteUseCaseParams, Unit>

data class UpdateMovieFavoriteUseCaseParams(
    val movie: Movie,
    val isFavorite: Boolean
)
