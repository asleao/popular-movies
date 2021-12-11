package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.services.movie.MovieService
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import br.com.popularmovies.datasourceremote.utils.mapApiResults
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    retrofit: Retrofit,
    private val retrofitResponse: RetrofitResponse
) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(orderBy: String): Result<List<MovieDto>> {
        return retrofitResponse
            .request { mMovieService.getMovies(orderBy) }
            .mapApiResults()

    }

    suspend fun getMovie(movieId: Int): Result<MovieDto> {
        return retrofitResponse
            .request { mMovieService.getMovie(movieId) }
            .mapApiResult()
    }

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewDto>> {
        return retrofitResponse
            .request { mMovieService.getMovieReviews(movieId) }
            .mapApiResults()
    }

    suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailerDto>> {
        return retrofitResponse
            .request { mMovieService.getMovieTrailers(movieId) }
            .mapApiResults()
    }
}