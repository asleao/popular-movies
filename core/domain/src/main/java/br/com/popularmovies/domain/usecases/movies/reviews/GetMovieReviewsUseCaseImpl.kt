package br.com.popularmovies.domain.usecases.movies.reviews

import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCaseParams
import br.com.popularmovies.model.movie.MovieReview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieReviewsUseCase {

    override fun build(param: GetMovieReviewsUseCaseParams): Flow<List<MovieReview>> {
        return movieRepository.getMovieReviews(param.movieId)
    }
}