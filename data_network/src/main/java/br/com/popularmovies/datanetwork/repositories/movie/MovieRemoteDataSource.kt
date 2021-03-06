package br.com.popularmovies.datanetwork.repositories.movie

import br.com.popularmovies.datanetwork.models.base.Result
import br.com.popularmovies.datanetwork.models.base.RetrofitResponse
import br.com.popularmovies.datanetwork.models.movie.MovieDto
import br.com.popularmovies.datanetwork.models.movie.MovieReviews
import br.com.popularmovies.datanetwork.models.movie.MovieTrailers
import br.com.popularmovies.datanetwork.models.movie.Movies
import br.com.popularmovies.datanetwork.services.movie.MovieService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(retrofit: Retrofit) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(orderBy: String): Result<Movies> {
        return RetrofitResponse { mMovieService.getMovies(orderBy) }.result()
    }

    suspend fun getMovie(movieId: Int): Result<MovieDto> {
        return RetrofitResponse { mMovieService.getMovie(movieId) }.result()
    }

    suspend fun getMovieReviews(movieId: Int): Result<MovieReviews> {
        return RetrofitResponse { mMovieService.getMovieReviews(movieId) }.result()
    }

    suspend fun getMovieTrailers(movieId: Int): Result<MovieTrailers> {
        return RetrofitResponse { mMovieService.getMovieTrailers(movieId) }.result()
    }
}
