package br.com.popularmovies.services.movieService.service

import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/{sort_by}")
    suspend fun getMovies(@Path("sort_by") orderBy: String): Response<Movies>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") movieId: Int): Response<MovieReviews>

    @GET("movie/{id}")
    fun getMovie(@Path("id") movieId: Int): Call<Movie>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(@Path("id") movieId: Int): Response<MovieTrailers>
}
