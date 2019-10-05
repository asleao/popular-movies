package br.com.popularmovies.services.movieService.source.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.popularmovies.core.network.GENERIC_ERROR_CODE
import br.com.popularmovies.core.network.local.AppDatabase
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.data.model.ErrorResponse
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.movies.Constants.ROOM_MSG_ERROR
import br.com.popularmovies.services.movieService.response.Movie
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.source.MovieDataSource
import br.com.popularmovies.utils.AppExecutors

class MovieLocalDataSource private constructor(context: Context) : MovieDataSource {
    private val mMovieDao: MovieDao = AppDatabase.getInstance(context).movieDao()

    override fun getMovies(orderBy: String): LiveData<OldResource<Movies>> {
        val movies = MediatorLiveData<OldResource<Movies>>()
        movies.postValue(OldResource.loading())
        try {
            movies.addSource(mMovieDao.movies) { fetchedMovies ->
                movies.postValue(
                    OldResource.success(
                        Movies(fetchedMovies)
                    )
                )
            }
        } catch (e: Exception) {
            movies.postValue(
                OldResource.error(
                    ErrorResponse(
                        GENERIC_ERROR_CODE,
                        ROOM_MSG_ERROR
                    )
                )
            )
        }

        return movies
    }

    override fun getMovie(movieId: Int): LiveData<OldResource<Movie>> {
        val movie = MediatorLiveData<OldResource<Movie>>()
        movie.postValue(OldResource.loading())
        try {
            movie.addSource(mMovieDao.getMovie(movieId)) { fetchedMovie ->
                movie.postValue(
                    OldResource.success(fetchedMovie)
                )
            }
        } catch (e: Exception) {
            movie.postValue(
                OldResource.error(
                    ErrorResponse(
                        GENERIC_ERROR_CODE,
                        ROOM_MSG_ERROR
                    )
                )
            )
        }

        return movie
    }

    override suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews> {
        return Resource.error(Error(1, "", ""))
    }

    override fun saveToFavorites(movieId: Int, status: Boolean): LiveData<OldResource<Boolean>> {
        val mMovie = MutableLiveData<OldResource<Boolean>>()
        mMovie.postValue(OldResource.loading())
        try {
            AppExecutors.getInstance().diskIO().execute {
                mMovieDao.saveFavorites(movieId, status)
                mMovie.postValue(OldResource.success(status))
            }
        } catch (e: Exception) {
            mMovie.postValue(
                OldResource.error(
                    ErrorResponse(
                        GENERIC_ERROR_CODE,
                        ROOM_MSG_ERROR
                    )
                )
            )
        }

        return mMovie
    }

    override fun saveMovies(movies: List<Movie>): LiveData<OldResource<Void>> {
        val mMovie = MutableLiveData<OldResource<Void>>()
        mMovie.postValue(OldResource.loading())
        try {
            AppExecutors.getInstance().diskIO().execute {
                mMovieDao.insertAllMovies(movies)
                mMovie.postValue(OldResource.success(null))
            }
        } catch (e: Exception) {
            mMovie.postValue(
                OldResource.error(
                    ErrorResponse(
                        GENERIC_ERROR_CODE,
                        ROOM_MSG_ERROR
                    )
                )
            )
        }

        return mMovie
    }

    override fun saveMovie(movie: Movie): LiveData<OldResource<Void>> {
        val mMovie = MutableLiveData<OldResource<Void>>()
        mMovie.postValue(OldResource.loading())
        try {
            AppExecutors.getInstance().diskIO().execute {
                mMovieDao.insertMovie(movie)
                mMovie.postValue(OldResource.success(null))
            }
        } catch (e: Exception) {
            mMovie.postValue(
                OldResource.error(
                    ErrorResponse(
                        GENERIC_ERROR_CODE,
                        ROOM_MSG_ERROR
                    )
                )
            )
        }

        return mMovie
    }

    override fun removeMovie(movie: Movie): LiveData<OldResource<Void>> {
        val mMovie = MutableLiveData<OldResource<Void>>()
        mMovie.postValue(OldResource.loading())
        try {
            AppExecutors.getInstance().diskIO().execute {
                mMovieDao.deleteMovie(movie)
                mMovie.postValue(OldResource.success(null))
            }
        } catch (e: Exception) {
            mMovie.postValue(
                OldResource.error(
                    ErrorResponse(
                        GENERIC_ERROR_CODE,
                        ROOM_MSG_ERROR
                    )
                )
            )
        }

        return mMovie
    }

    override fun getMovieTrailers(movieId: Int): LiveData<OldResource<MovieTrailers>> {
        return MutableLiveData()
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieLocalDataSource? = null

        fun getInstance(context: Context): MovieLocalDataSource? {
            if (INSTANCE == null) {
                synchronized(MovieLocalDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieLocalDataSource(context)
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
