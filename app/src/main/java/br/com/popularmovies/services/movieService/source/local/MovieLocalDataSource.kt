package br.com.popularmovies.services.movieService.source.local

import androidx.lifecycle.LiveData
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
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(appDatabase: AppDatabase) {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    private val error = Error(
            codErro = GENERIC_ERROR_CODE,
            title = GENERIC_MSG_ERROR_TITLE,
            message = GENERIC_MSG_ERROR_MESSAGE
    )

    suspend fun getMovies(): Resource<Movies> {
        return try {
            Resource.success(Movies(mMovieDao.movies()))
        } catch (exception: Exception) {
            Resource.error(error)
        }
    }

    suspend fun getFavoriteMovies(isFavorite: Boolean): Resource<Movies> {
        return try {
            Resource.success(Movies(mMovieDao.getFavoriteMovies(isFavorite)))
        } catch (exception: Exception) {
            Resource.error(error)
        }
    }

    suspend fun getMovie(movieId: Int): Resource<Movie> {
        return try {
            Resource.success(mMovieDao.getMovie(movieId))
        } catch (e: Exception) {
            Resource.error(Error(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }


    suspend fun saveToFavorites(movie: Movie): Resource<Boolean> {
        return try {
            mMovieDao
                    .saveFavorites(movie.id, movie.isFavorite)
                    .run {
                        Resource.success(movie.isFavorite)
                    }
        } catch (e: Exception) {
            Resource.error(Error(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    fun insertMovies(movies: List<Movie>): LiveData<OldResource<Void>> {
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

    suspend fun insertMovie(movie: Movie): Resource<Unit> {
        return try {
            Resource.success(mMovieDao.insertMovie(movie))
        } catch (e: Exception) {
            Resource.error(Error(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun isMovieExists(movieId: Int): Resource<Boolean> {
        return try {
            Resource.success(mMovieDao.isMovieExists(movieId))
        } catch (e: Exception) {
            Resource.error(Error(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers> {
        return Resource.success(MovieTrailers())
    }
}
