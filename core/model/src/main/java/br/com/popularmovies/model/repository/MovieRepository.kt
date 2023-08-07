package br.com.popularmovies.model.repository

import androidx.paging.PagingData
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.movie.MovieTrailer
import br.com.popularmovies.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
//TODO Move this to domain module?
interface MovieRepository {

    fun getMovies(movieType: MovieType): Flow<PagingData<Movie>>

    suspend fun getRandomNowPlayingMovies(): Result<List<Movie>>

    suspend fun getMovie(movieId: Long): Result<Movie>

    suspend fun getMovieReviews(movieId: Long): Result<List<MovieReview>>

    suspend fun saveToFavorites(movie: Movie): Result<Unit>

    suspend fun getMovieTrailers(movieId: Long): Result<List<MovieTrailer>>
}