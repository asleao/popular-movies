package br.com.popularmovies.services.movieService.source.local

import br.com.popularmovies.core.network.local.AppDatabase
import br.com.popularmovies.datanetwork.config.GENERIC_ERROR_CODE
import br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.datanetwork.config.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.datanetwork.models.base.AppError
import br.com.popularmovies.datanetwork.models.base.Result
import br.com.popularmovies.movies.Constants.ROOM_MSG_ERROR
import br.com.popularmovies.services.movieService.response.MovieTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(appDatabase: AppDatabase) {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    private val error = AppError(
            codErro = GENERIC_ERROR_CODE,
            title = GENERIC_MSG_ERROR_TITLE,
            message = GENERIC_MSG_ERROR_MESSAGE
    )

    suspend fun getMovies(): Result<List<MovieTable>> {
        return try {
            Result.Success(mMovieDao.movies())
        } catch (exception: Exception) {
            Result.Error(error)
        }
    }

    suspend fun getFavoriteMovies(isFavorite: Boolean): Result<List<MovieTable>> {
        return try {
            Result.Success(mMovieDao.getFavoriteMovies(isFavorite))
        } catch (exception: Exception) {
            Result.Error(error)
        }
    }

    suspend fun getMovie(movieId: Int): Result<MovieTable> {
        return try {
            Result.Success(mMovieDao.getMovie(movieId))
        } catch (e: Exception) {
            Result.Error(AppError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }


    suspend fun saveToFavorites(movieTable: MovieTable): Result<Unit> {
        return try {
            val updatedRowsCount = mMovieDao.saveFavorites(movieTable.id, movieTable.isFavorite)
            Result.Success(updatedRowsCount)

        } catch (e: Exception) {
            Result.Error(AppError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun insertMovie(movieTable: MovieTable): Result<Unit> {
        return try {
            Result.Success(mMovieDao.insertMovie(movieTable))
        } catch (e: Exception) {
            Result.Error(AppError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun isMovieExists(movieId: Int): Result<Boolean> {
        return try {
            Result.Success(mMovieDao.isMovieExists(movieId))
        } catch (e: Exception) {
            Result.Error(AppError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }
}
