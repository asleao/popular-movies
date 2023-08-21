package br.com.popularmovies.datasourceremoteapi

import br.com.popularmovies.datasourceremoteapi.models.movie.MovieDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTypeParam
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int, type: MovieTypeParam): List<MovieDto>
    fun getMovie(movieId: Long): Flow<MovieDto>
    fun getMovieReviews(movieId: Long): Flow<List<MovieReviewDto>>
    fun getMovieTrailers(movieId: Long): Flow<List<MovieTrailerDto>>
    fun getNowPlayingMovies(page: Int): Flow<List<MovieDto>>
}