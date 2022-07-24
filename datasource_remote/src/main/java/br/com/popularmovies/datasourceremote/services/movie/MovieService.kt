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

    @GET("movie/popular") //TODO Check how to use Path followed by a Query to use one method instead
    suspend fun getPopularMovies(@Query("page") page: Int): Response<BaseDto<MovieDto>>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<BaseDto<MovieDto>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<BaseDto<MovieDto>>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") movieId: Long): Response<BaseDto<MovieReviewDto>>

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") movieId: Long): Response<MovieDto>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(@Path("id") movieId: Long): Response<BaseDto<MovieTrailerDto>>
}
