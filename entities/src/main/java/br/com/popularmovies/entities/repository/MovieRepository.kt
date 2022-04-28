package br.com.popularmovies.entities.repository

import androidx.paging.PagingData
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    suspend fun getRandomNowPlayingMovie(): Result<Movie>

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getTopHatedMovies(): Flow<PagingData<Movie>>

    suspend fun getMovie(movieId: Long): Result<Movie>

    suspend fun getMovieReviews(movieId: Long): Result<List<MovieReview>>

    suspend fun saveToFavorites(movie: Movie): Result<Unit>

    suspend fun getMovieTrailers(movieId: Long): Result<List<MovieTrailer>>
}