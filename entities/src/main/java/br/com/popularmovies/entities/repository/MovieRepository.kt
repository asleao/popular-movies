package br.com.popularmovies.entities.repository

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer

interface MovieRepository {
    suspend fun getMovies(orderBy: String): List<Movie>

    suspend fun getMovie(movie: Movie): Movie

    suspend fun getMovieReviews(movie: Movie): List<MovieReview>

    suspend fun saveToFavorites(movie: Movie)

    suspend fun getMovieTrailers(movie: Movie): List<MovieTrailer>
}