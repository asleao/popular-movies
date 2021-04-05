package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.services.movie.MovieService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(retrofit: Retrofit) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(orderBy: String): Result<List<MovieDto>> {
        return when (val result = RetrofitResponse { mMovieService.getMovies(orderBy) }.result()) {
            is Result.Success -> {
                Result.Success(result.data.results)
            }
            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }

    suspend fun getMovie(movieId: Int): Result<MovieDto> {
        return RetrofitResponse { mMovieService.getMovie(movieId) }.result()
    }

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewDto>> {
        return when (val result = RetrofitResponse { mMovieService.getMovieReviews(movieId) }.result()) {
            is Result.Success -> {
                Result.Success(result.data.results)
            }
            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }

    suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailerDto>> {
        return when (val result = RetrofitResponse { mMovieService.getMovieTrailers(movieId) }.result()) {
            is Result.Success -> {
                Result.Success(result.data.results)
            }
            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}
