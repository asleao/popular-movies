package br.com.popularmovies.datasourcedb.datasources.movie

import androidx.paging.PagingSource
import androidx.room.withTransaction
import br.com.popularmovies.common.configs.ErrorCodes.GENERIC_ERROR_CODE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_MESSAGE
import br.com.popularmovies.common.configs.ErrorMessages.GENERIC_MSG_ERROR_TITLE
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.datasourcedb.config.DbConstants.ROOM_MSG_ERROR
import br.com.popularmovies.datasourcedb.daos.MovieDao
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    private val error = NetworkError(
        code = GENERIC_ERROR_CODE,
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

    fun getPopularMoviesPagingSourceFactory(): PagingSource<Int, MovieTable> {
        return mMovieDao.getPopularMovies()
    }

    suspend fun getFavoriteMovies(isFavorite: Boolean): Result<List<MovieTable>> {
        return try {
            Result.Success(mMovieDao.getFavoriteMovies(isFavorite))
        } catch (exception: Exception) {
            Result.Error(error)
        }
    }

    suspend fun getMovie(movieId: Long): Result<MovieTable> {
        return try {
            Result.Success(mMovieDao.getMovie(movieId))
        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }


    suspend fun saveToFavorites(movieTable: MovieTable): Result<Unit> {
        return try {
            val updatedRowsCount = mMovieDao.saveFavorites(movieTable.id, movieTable.isFavorite)
            Result.Success(updatedRowsCount)

        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun insertMovie(movieTable: MovieTable): Result<Unit> {
        return try {
            Result.Success(mMovieDao.insertMovie(movieTable))
        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun isMovieExists(movieId: Long): Result<Boolean> {
        return try {
            Result.Success(mMovieDao.isMovieExists(movieId))
        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun deleteAllMovies() {
        appDatabase.withTransaction {
            mMovieDao.deleteAllMovies()
        }
    }

    suspend fun insertAllMovies(movies: List<MovieTable>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllMovies(movies)
        }
    }
}
