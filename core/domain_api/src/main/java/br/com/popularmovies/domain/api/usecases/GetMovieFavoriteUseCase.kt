package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieFavorite

interface GetMovieFavoriteUseCase : FlowUseCase<GetMovieFavoriteUseCaseParams, MovieFavorite>

data class GetMovieFavoriteUseCaseParams(
    val movieId: Long
)