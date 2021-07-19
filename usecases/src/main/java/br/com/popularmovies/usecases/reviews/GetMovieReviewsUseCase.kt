package br.com.popularmovies.usecases.reviews

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.repositories.MovieRepository

class GetMovieReviewsUseCase(private val movieRepository: MovieRepository) {

    suspend fun build(param: Params): List<MovieReview> {
        return movieRepository.getMovieReviews(param.movie)
    }

    data class Params(
        val movie: Movie
    )
}

