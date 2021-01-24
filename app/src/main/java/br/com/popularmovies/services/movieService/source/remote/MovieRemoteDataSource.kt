package br.com.popularmovies.services.movieService.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.core.network.retrofit.model.RetrofitResponse
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.service.MovieService
import br.com.popularmovies.services.movieService.source.ApiResponse
import br.com.popularmovies.services.movieService.source.MovieDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(retrofit: Retrofit) : MovieDataSource {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    override suspend fun getMovies(orderBy: String): Resource<Movies> {
        return RetrofitResponse { mMovieService.getMovies(orderBy) }.result()
    }

    override fun getMovie(movieId: Int): LiveData<OldResource<Movie>> {
        val call = mMovieService.getMovie(movieId)
        val apiResponse = ApiResponse<Movie>("getMovie")
        val movie = MutableLiveData<OldResource<Movie>>()
        movie.value = OldResource.loading()
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                movie.value = apiResponse.getApiOnResponse(response)
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                movie.value = apiResponse.getApiOnFailure(t)
            }
        })
        return movie
    }

    override suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews> {
        return RetrofitResponse { mMovieService.getMovieReviews(movieId) }.result()
    }

    override fun saveToFavorites(movieId: Int, status: Boolean): LiveData<OldResource<Boolean>> {
        return MutableLiveData()
    }

    override fun saveMovies(movies: List<Movie>): LiveData<OldResource<Void>> {
        return MutableLiveData()
    }

    override fun saveMovie(movie: Movie): LiveData<OldResource<Void>> {
        return MutableLiveData()
    }

    override fun removeMovie(movie: Movie): LiveData<OldResource<Void>> {
        return MutableLiveData()
    }

    override suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers> {
        return RetrofitResponse { mMovieService.getMovieTrailers(movieId) }.result()
    }
}
