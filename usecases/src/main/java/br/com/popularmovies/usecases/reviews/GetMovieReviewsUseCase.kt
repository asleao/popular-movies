package br.com.popularmovies.usecases.reviews

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.UseCase
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieReviewsUseCase.Params, List<MovieReview>>() {

    override suspend fun build(param: Params): List<MovieReview> {
        return movieRepository.getMovieReviews(param.movie)
    }

    data class Params(
        val movie: Movie
    )
}