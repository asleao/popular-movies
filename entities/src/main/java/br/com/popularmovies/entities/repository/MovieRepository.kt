package br.com.popularmovies.entities.repository

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieOrderType
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer

interface MovieRepository {
    suspend fun getMovies(orderBy: MovieOrderType): Result<List<Movie>>

    suspend fun getMovie(movieId: Int): Result<Movie>

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReview>>

    suspend fun saveToFavorites(movie: Movie): Result<Unit>

    suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailer>>
}