package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.services.movie.MovieService
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import br.com.popularmovies.datasourceremote.utils.mapApiResults
import br.com.popularmovies.datasourceremote.utils.request
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(retrofit: Retrofit) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(orderBy: String): Result<List<MovieDto>> {
        return mMovieService.getMovies(orderBy)
            .request()
            .mapApiResults()
    }

    suspend fun getMovie(movieId: Int): Result<MovieDto> {
        return mMovieService.getMovie(movieId)
            .request()
            .mapApiResult()
    }

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewDto>> {
        return mMovieService.getMovieReviews(movieId)
            .request()
            .mapApiResults()
    }

    suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailerDto>> {
        return mMovieService.getMovieTrailers(movieId)
            .request()
            .mapApiResults()
    }
}