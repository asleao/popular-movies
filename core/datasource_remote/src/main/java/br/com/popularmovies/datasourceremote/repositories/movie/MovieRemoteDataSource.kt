package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTypeParam

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int, type: MovieTypeParam): Result<List<MovieDto>>
    suspend fun getMovie(movieId: Long): Result<MovieDto>
    suspend fun getMovieReviews(movieId: Long): Result<List<MovieReviewDto>>
    suspend fun getMovieTrailers(movieId: Long): Result<List<MovieTrailerDto>>
    suspend fun getNowPlayingMovies(page: Int): Result<List<MovieDto>>
}