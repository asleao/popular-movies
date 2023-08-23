package br.com.popularmovies.datasourceremoteapi

import br.com.popularmovies.datasourceremoteapi.models.movie.MovieDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTypeParam
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int, type: MovieTypeParam): List<MovieDto>
    suspend fun getMovie(movieId: Long): MovieDto
    suspend fun getMovieReviews(movieId: Long): List<MovieReviewDto>
    suspend fun getMovieTrailers(movieId: Long): List<MovieTrailerDto>
    fun getNowPlayingMovies(page: Int): Flow<List<MovieDto>>
}