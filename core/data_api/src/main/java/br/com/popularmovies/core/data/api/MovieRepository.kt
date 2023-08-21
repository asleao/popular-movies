package br.com.popularmovies.core.data.api

import androidx.paging.PagingData
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.movie.MovieTrailer
import br.com.popularmovies.model.movie.MovieType
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(movieType: MovieType): Flow<PagingData<Movie>>

    fun getRandomNowPlayingMovies(): Flow<List<Movie>>

    fun getMovie(movieId: Long): Flow<Movie>

    fun getMovieReviews(movieId: Long): Flow<List<MovieReview>>

    fun saveToFavorites(movie: Movie)

    fun getMovieTrailers(movieId: Long): Flow<List<MovieTrailer>>
}