package br.com.popularmovies.services.movieService

import br.com.popularmovies.datasourceremote.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer


interface MovieRepository {

    suspend fun getMovies(orderBy: String): Result<List<Movie>>

    suspend fun getMovie(movieId: Int): Result<Movie> //Same as getMovieReviews

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReview>>// Refactor to receive entire object as a parameter

    suspend fun saveToFavorites(movie: Movie): Result<Unit>

    suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailer>> //Same as getMovieReviews
}
