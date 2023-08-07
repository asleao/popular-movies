package br.com.popularmovies.domain.usecases.movies.reviews

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.repository.MovieRepository
import br.com.popularmovies.domain.usecases.UseCase
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieReviewsUseCase.Params, List<MovieReview>>() {

    override suspend fun build(param: Params): Result<List<MovieReview>> {
        return movieRepository.getMovieReviews(param.movieId)
    }

    data class Params(
        val movieId: Long
    )
}