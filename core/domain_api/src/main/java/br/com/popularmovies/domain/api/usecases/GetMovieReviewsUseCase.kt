package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.UseCase
import br.com.popularmovies.model.movie.MovieReview

interface GetMovieReviewsUseCase : UseCase<GetMovieReviewsUseCaseParams, List<MovieReview>>

data class GetMovieReviewsUseCaseParams(
    val movieId: Long
)
