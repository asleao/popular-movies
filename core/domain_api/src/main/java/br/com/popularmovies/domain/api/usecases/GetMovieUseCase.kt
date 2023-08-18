package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.UseCase
import br.com.popularmovies.model.movie.Movie

interface GetMovieUseCase : UseCase<GetMovieUseCaseParams, Movie>

data class GetMovieUseCaseParams(
    val movieId: Long
)