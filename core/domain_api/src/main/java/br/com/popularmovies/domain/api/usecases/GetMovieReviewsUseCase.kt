package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.model.movie.MovieReview

interface GetMovieReviewsUseCase : FlowUseCase<GetMovieReviewsUseCaseParams, List<MovieReview>>

data class GetMovieReviewsUseCaseParams(
    val movieId: Long
)
