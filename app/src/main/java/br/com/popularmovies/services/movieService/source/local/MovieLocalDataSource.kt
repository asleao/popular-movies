package br.com.popularmovies.services.movieService.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.popularmovies.core.network.GENERIC_ERROR_CODE
import br.com.popularmovies.core.network.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.core.network.GENERIC_MSG_ERROR_TITLE
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(appDatabase: AppDatabase) : MovieDataSource {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    private val error = Error(
            codErro = GENERIC_ERROR_CODE,
            title = GENERIC_MSG_ERROR_TITLE,
            message = GENERIC_MSG_ERROR_MESSAGE
    )

    override suspend fun getMovies(orderBy: String): Resource<Movies> {
        return try {
            Resource.success(Movies(mMovieDao.movies()))
        } catch (exception: Exception) {
            Resource.error(error)
        }
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
}
