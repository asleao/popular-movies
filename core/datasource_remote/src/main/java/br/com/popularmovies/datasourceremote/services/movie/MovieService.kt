package br.com.popularmovies.datasourceremote.services.movie

import br.com.popularmovies.datasourceremote.models.base.BaseDto
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{movie-type}")
    suspend fun getMovies(
        @Path("movie-type") movieType: String,
        @Query("page") page: Int
    ): Response<BaseDto<MovieDto>>

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") movieId: Long): Response<MovieDto>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") movieId: Long): Response<BaseDto<MovieReviewDto>>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(@Path("id") movieId: Long): Response<BaseDto<MovieTrailerDto>>
}
