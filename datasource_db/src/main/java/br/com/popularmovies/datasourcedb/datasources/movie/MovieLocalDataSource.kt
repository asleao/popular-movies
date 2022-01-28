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
import org.joda.time.LocalDate
import java.math.BigDecimal
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

    fun getPopularMoviesPagingSourceFactory(): PagingSource<Int, MovieTable.MostPopular> {
        return mMovieDao.mostPopularMovies()
    }

    fun getTopRatedMoviesPagingSourceFactory(): PagingSource<Int, MovieTable.TopRated> {
        return mMovieDao.topRatedMovies()
    }

    fun getNowPlayingMoviesPagingSourceFactory(): PagingSource<Int, MovieTable.NowPlaying> {
        return mMovieDao.nowPlayingMovies()
    }

    suspend fun getMovie(movieId: Long): Result<MovieTable> {
        return try {
//            Result.Success(mMovieDao.getMovie(movieId)) ////TODO Check That
            Result.Success(
                MovieTable.MostPopular(
                    1, 1, BigDecimal.ZERO, "", BigDecimal.ONE, "", "",
                    LocalDate.now(), 1, false
                )
            )
        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun saveToFavorites(movieTable: MovieTable): Result<Unit> {
        return try {
//            val updatedRowsCount = mMovieDao.saveFavorites(movieTable.id, movieTable.isFavorite) //TODO Check That
            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun insertMovie(movieTable: MovieTable): Result<Unit> {
        return try {
//            Result.Success(mMovieDao.insertMovie(movieTable)) //TODO Check That
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun isMovieExists(movieId: Long): Result<Boolean> {
        return try {
//            Result.Success(mMovieDao.isMovieExists(movieId)) //TODO Check That
            Result.Success(false)
        } catch (e: Exception) {
            Result.Error(NetworkError(GENERIC_ERROR_CODE, ROOM_MSG_ERROR))
        }
    }

    suspend fun deleteAllMostPopularMovies() {
        appDatabase.withTransaction {
            mMovieDao.deleteAllMostPopularMovies()
        }
    }

    suspend fun deleteAllTopRatedMovies() {
        appDatabase.withTransaction {
            mMovieDao.deleteAllTopRatedMovies()
        }
    }

    suspend fun deleteAllNowPlayingMovies() {
        appDatabase.withTransaction {
            mMovieDao.deleteAllNowPlayingMovies()
        }
    }

    suspend fun insertAllMostPopularMovies(movies: List<MovieTable.MostPopular>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllMostPopularMovies(movies)
        }
    }

    suspend fun insertAllTopRatedMovies(movies: List<MovieTable.TopRated>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllTopRatedMovies(movies)
        }
    }

    suspend fun insertAllNowPlayingMovies(movies: List<MovieTable.NowPlaying>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllNowPlayingMovies(movies)
        }
    }
}
