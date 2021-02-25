package br.com.popularmovies.services.movieService.source.remote

import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.core.network.retrofit.model.RetrofitResponse
import br.com.popularmovies.services.movieService.response.MovieDto
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.service.MovieService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(retrofit: Retrofit) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(orderBy: String): Resource<Movies> {
        return RetrofitResponse { mMovieService.getMovies(orderBy) }.result()
    }

    suspend fun getMovie(movieId: Int): Resource<MovieDto> {
        return RetrofitResponse { mMovieService.getMovie(movieId) }.result()
    }

    suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews> {
        return RetrofitResponse { mMovieService.getMovieReviews(movieId) }.result()
    }

    suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers> {
        return RetrofitResponse { mMovieService.getMovieTrailers(movieId) }.result()
    }
}
