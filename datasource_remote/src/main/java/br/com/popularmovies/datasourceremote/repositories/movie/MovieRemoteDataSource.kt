package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.services.movie.MovieService
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import br.com.popularmovies.datasourceremote.utils.mapApiResults
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(retrofit: Retrofit) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(orderBy: String): Result<List<MovieDto>> {
        return RetrofitResponse { mMovieService.getMovies(orderBy) }
            .result()
            .mapApiResults()
    }

    suspend fun getMovie(movieId: Int): Result<MovieDto> {
        return RetrofitResponse { mMovieService.getMovie(movieId) }
            .result()
            .mapApiResult()
    }

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewDto>> {
        return RetrofitResponse { mMovieService.getMovieReviews(movieId) }
            .result()
            .mapApiResults()
    }

    suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailerDto>> {
        return RetrofitResponse { mMovieService.getMovieTrailers(movieId) }
            .result()
            .mapApiResults()
    }
}