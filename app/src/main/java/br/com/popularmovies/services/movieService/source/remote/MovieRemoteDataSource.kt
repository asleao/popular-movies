package br.com.popularmovies.services.movieService.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import br.com.popularmovies.core.network.retrofit.ServiceGenerator
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor() : MovieDataSource {
    private val mMovieService: MovieService =
        ServiceGenerator.createService(MovieService::class.java)

    override fun getMovies(orderBy: String): LiveData<OldResource<Movies>> {
        val call = mMovieService.getMovies(orderBy)
        val apiResponse = ApiResponse<Movies>("getMovies")
        val movies = MutableLiveData<OldResource<Movies>>()
        movies.value = OldResource.loading()
        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                movies.value = apiResponse.getApiOnResponse(response)
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                movies.value = apiResponse.getApiOnFailure(t)
            }
        })
        return movies
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

    override fun getMovieTrailers(movieId: Int): LiveData<OldResource<MovieTrailers>> {
        val call = mMovieService.getMovieTrailers(movieId)
        val apiResponse = ApiResponse<MovieTrailers>("getMovieTrailers")
        val trailers = MutableLiveData<OldResource<MovieTrailers>>()
        trailers.value = OldResource.loading()
        call.enqueue(object : Callback<MovieTrailers> {
            override fun onResponse(call: Call<MovieTrailers>, response: Response<MovieTrailers>) {
                trailers.value = apiResponse.getApiOnResponse(response)
            }

            override fun onFailure(call: Call<MovieTrailers>, t: Throwable) {
                trailers.value = apiResponse.getApiOnFailure(t)
            }
        })
        return trailers
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieRemoteDataSource? = null

        val instance: MovieRemoteDataSource?
            get() {
                if (INSTANCE == null) {
                    synchronized(MovieRemoteDataSource::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = MovieRemoteDataSource()
                        }
                    }
                }
                return INSTANCE
            }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
