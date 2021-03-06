package br.com.popularmovies.services.movieService

import br.com.popularmovies.datanetwork.models.base.Result
import br.com.popularmovies.datanetwork.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.movies.Constants.FILTER_FAVORITES
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.mappers.toDomain
import br.com.popularmovies.services.movieService.source.mappers.toTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
        private val mMovieLocalDataSource: MovieLocalDataSource,
        private val mMovieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getMovies(orderBy: String): Result<List<Movie>> {

        return if (orderBy == FILTER_FAVORITES) {
            when (val result = mMovieLocalDataSource.getFavoriteMovies(true)) {
                is Result.Success -> Result.Success(result.data.map { it.toDomain() })
                is Result.Error -> Result.Error(result.error)
            }
        } else {
            when (val result = mMovieRemoteDataSource.getMovies(orderBy)) {
                is Result.Success -> Result.Success(result.data.movieDtos.map { it.toDomain() })
                is Result.Error -> Result.Error(result.error)
            }
        }
    }

    override suspend fun getMovie(movieId: Int): Result<Movie> {
        return when (val result = mMovieRemoteDataSource.getMovie(movieId)) {
            is Result.Success -> Result.Success(result.data.toDomain())
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun getMovieReviews(movieId: Int): Result<List<MovieReview>> {
        return when (val result = mMovieRemoteDataSource.getMovieReviews(movieId)) {
            is Result.Success -> Result.Success(result.data.reviewDtos.map { it.toDomain() })
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun saveToFavorites(movie: Movie): Result<Unit> {
        // when caching is implemented this will only saveToFavorites
        return when (val result = mMovieLocalDataSource.isMovieExists(movie.id)) {
            is Result.Success -> {
                val isMovieExists = result.data
                if (isMovieExists) {
                    when (val result = mMovieLocalDataSource.saveToFavorites(movie.toTable())) {
                        is Result.Success -> Result.Success(Unit)
                        is Result.Error -> Result.Error(result.error)
                    }
                } else {
                    when (val result = mMovieLocalDataSource.insertMovie(movie.toTable())) {
                        is Result.Success -> Result.Success(Unit)
                        is Result.Error -> Result.Error(result.error)
                    }
                }
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun getMovieTrailers(movieId: Int): Result<List<MovieTrailer>> {
        return when (val result = mMovieRemoteDataSource.getMovieTrailers(movieId)) {
            is Result.Success -> Result.Success(result.data.trailerDtos.map { it.toDomain() })
            is Result.Error -> Result.Error(result.error)
        }
    }
}
