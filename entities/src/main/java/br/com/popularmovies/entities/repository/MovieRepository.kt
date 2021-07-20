package br.com.popularmovies.entities.repository

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer

interface MovieRepository {
    suspend fun getMovies(orderBy: String): List<Movie>

    suspend fun getMovie(movieId: Int): Movie

    suspend fun getMovieReviews(movieId: Int): List<MovieReview>

    suspend fun saveToFavorites(movie: Movie)

    suspend fun getMovieTrailers(movieId: Int): List<MovieTrailer>
}