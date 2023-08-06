package br.com.popularmovies.datasourcedb.datasources.movie

import androidx.paging.PagingSource
import androidx.room.withTransaction
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.datasourcedb.daos.MovieDao
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable> {
        return mMovieDao.movies(type)
    }

    suspend fun deleteAllMovies(type: MovieTypeTable) {
        appDatabase.withTransaction {
            mMovieDao.deleteAllMovies(type)
        }
    }

    suspend fun insertAllMovies(movies: List<MovieTable>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllMovies(movies)
        }
    }
}
