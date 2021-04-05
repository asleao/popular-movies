package br.com.popularmovies.datasourceremote.services.movie

import br.com.popularmovies.datasourceremote.models.base.BaseDto
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/{sort_by}")
    suspend fun getMovies(@Path("sort_by") orderBy: String): Response<BaseDto<MovieDto>>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") movieId: Int): Response<BaseDto<MovieReviewDto>>

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") movieId: Int): Response<MovieDto>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(@Path("id") movieId: Int): Response<MovieTrailers>
}
