package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.domain.api.usecases.base.UseCase
import br.com.popularmovies.model.movie.MovieTrailer

interface GetMovieTrailersUseCase : FlowUseCase<GetMovieTrailersUseCaseParams, List<MovieTrailer>>

data class GetMovieTrailersUseCaseParams(
    val movieId: Long
)
