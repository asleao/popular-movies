package br.com.popularmovies.datasourcedb.datasources.movie

import androidx.paging.PagingSource
import androidx.room.withTransaction
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.core.api.models.reviews.ReviewTable
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.datasourcedb.daos.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    MovieLocalDataSource {
    private val mMovieDao: MovieDao = appDatabase.movieDao()

    override fun getMoviesPagingSourceFactory(type: MovieTypeTable): PagingSource<Int, MovieTable> {
        return mMovieDao.movies(type)
    }

    override fun getMovies(type: MovieTypeTable): Flow<List<MovieTable>> {
        return mMovieDao.moviesList(type)
    }

    override suspend fun deleteAllMovies(type: MovieTypeTable) {
        appDatabase.withTransaction {
            mMovieDao.deleteAllMovies(type)
        }
    }

    override suspend fun getMovie(movieId: Long): MovieTable? {
        return appDatabase.withTransaction { mMovieDao.getMovie(movieId) }
    }

    override suspend fun deleteMovie(movieId: Long) {
        appDatabase.withTransaction {
            mMovieDao.deleteMovie(movieId)
        }
    }

    override suspend fun insertMovie(movie: MovieTable) {
        appDatabase.withTransaction {
            mMovieDao.insertMovie(movie)
        }
    }

    override suspend fun insertAllMovies(movies: List<MovieTable>) {
        appDatabase.withTransaction {
            mMovieDao.insertAllMovies(movies)
        }
    }

    override suspend fun getMovieReviews(movieId: Long): List<ReviewTable> {
        return appDatabase.withTransaction {
            mMovieDao.getMovieReviews(movieId).reviews
        }
    }

    override suspend fun insertMovieReviews(reviews: List<ReviewTable>) {
        appDatabase.withTransaction {
            mMovieDao.insertReviews(reviews)
        }
    }

    override suspend fun deleteMovieReviews(movieId: Long) {
        appDatabase.withTransaction {
            mMovieDao.deleteMovieReviews(movieId)
        }
    }
}
