package br.com.popularmovies.repositories.movie

import androidx.paging.*
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourcedb.datasources.keys.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSource
import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.repositories.config.PaginationConfig
import br.com.popularmovies.repositories.mappers.toDomain
import br.com.popularmovies.repositories.mappers.toMostPopularTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val mMovieLocalDataSource: MovieLocalDataSource,
    private val mMovieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationConfig.pageSize,
                enablePlaceholders = true
            ),
            remoteMediator = NowPlayingMoviesRemoteMediator(
                remoteKeyLocalDataSource,
                mMovieLocalDataSource,
                mMovieRemoteDataSource
            ),
            pagingSourceFactory = {
                mMovieLocalDataSource.getNowPlayingMoviesPagingSourceFactory()
            }
        )
            .flow
            .map { data ->
                data.map { movieTable ->
                    movieTable.toDomain()
                }
            }
    }

    override suspend fun getRandomNowPlayingMovies(): Result<List<Movie>> {
        return when (val result =
            mMovieRemoteDataSource.getNowPlayingMovies(PaginationConfig.defaultPage)) {
            is Result.Success -> Result.Success(result.data.take(5).map { it.toDomain() })
            is Result.Error -> Result.Error(result.error)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationConfig.pageSize,
                enablePlaceholders = true
            ),
            remoteMediator = PopularMoviesRemoteMediator(
                remoteKeyLocalDataSource,
                mMovieLocalDataSource,
                mMovieRemoteDataSource
            ),
            pagingSourceFactory = {
                mMovieLocalDataSource.getPopularMoviesPagingSourceFactory()
            }
        )
            .flow
            .map { data ->
                data.map { movieTable ->
                    movieTable.toDomain()
                }
            }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PaginationConfig.pageSize,
                enablePlaceholders = true
            ),
            remoteMediator = TopRatedMoviesRemoteMediator(
                remoteKeyLocalDataSource,
                mMovieLocalDataSource,
                mMovieRemoteDataSource
            ),
            pagingSourceFactory = {
                mMovieLocalDataSource.getTopRatedMoviesPagingSourceFactory()
            }
        )
            .flow
            .map { data ->
                data.map { movieTable ->
                    movieTable.toDomain()
                }
            }
    }

    override suspend fun getMovie(movieId: Long): Result<Movie> {
        return when (val result = mMovieLocalDataSource.getMovie(movieId)) {
            is Result.Success -> {
                if (result.data == null) {
                    when (val result = mMovieRemoteDataSource.getMovie(movieId)) {
                        is Result.Success -> Result.Success(result.data.toDomain())
                        is Result.Error -> Result.Error(result.error)
                    }
                } else {
                    Result.Success(result.data.toDomain())
                }
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun getMovieReviews(movieId: Long): Result<List<MovieReview>> {
        return when (val result = mMovieRemoteDataSource.getMovieReviews(movieId)) {
            is Result.Success -> Result.Success(result.data.map { it.toDomain() })
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun saveToFavorites(movie: Movie): Result<Unit> {
        // when caching is implemented this will only saveToFavorites
        return when (val result = mMovieLocalDataSource.isMovieExists(movie.id)) {
            is Result.Success -> {
                val isMovieExists = result.data
                if (isMovieExists) {
                    when (val result =
                        mMovieLocalDataSource.saveToFavorites(movie.toMostPopularTable())) { //Check That
                        is Result.Success -> Result.Success(Unit)
                        is Result.Error -> Result.Error(result.error)
                    }
                } else {
                    when (val result =
                        mMovieLocalDataSource.insertMovie(movie.toMostPopularTable())) { //Check That
                        is Result.Success -> Result.Success(Unit)
                        is Result.Error -> Result.Error(result.error)
                    }
                }
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun getMovieTrailers(movieId: Long): Result<List<MovieTrailer>> {
        return when (val result = mMovieRemoteDataSource.getMovieTrailers(movieId)) {
            is Result.Success -> Result.Success(result.data.map { it.toDomain() })
            is Result.Error -> Result.Error(result.error)
        }
    }
}