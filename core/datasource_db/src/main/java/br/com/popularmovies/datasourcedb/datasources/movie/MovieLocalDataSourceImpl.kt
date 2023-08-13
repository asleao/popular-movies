package br.com.popularmovies.datasourcedb.datasources.movie

import androidx.paging.PagingSource
import androidx.room.withTransaction
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.datasourcedb.daos.MovieDao
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase):
    MovieLocalDataSource {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    override fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable> {
        return mMovieDao.movies(type)
    }

    override suspend fun deleteAllMovies(type: MovieTypeTable) {
        appDatabase.withTransaction {
            mMovieDao.deleteAllMovies(type)
        }
    }

    override suspend fun insertAllMovies(movies: List<MovieTable>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllMovies(movies)
        }
    }
}
