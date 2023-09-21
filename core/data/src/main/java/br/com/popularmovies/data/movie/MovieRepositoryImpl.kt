package br.com.popularmovies.data.movie

import androidx.paging.*
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.core.api.RemoteKeyLocalDataSource
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.core.api.models.review.ReviewTable
import br.com.popularmovies.core.api.models.trailer.TrailerTable
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.data.config.PaginationConfig
import br.com.popularmovies.data.mappers.toDomain
import br.com.popularmovies.data.mappers.toParam
import br.com.popularmovies.data.mappers.toTable
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieFavorite
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.movie.MovieTrailer
import br.com.popularmovies.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val mMovieLocalDataSource: MovieLocalDataSource,
    private val mMovieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = PaginationConfig.prefechDistance,
                pageSize = PaginationConfig.pageSize,
                enablePlaceholders = true
            ),
            remoteMediator = MoviesRemoteMediator(
                movieType.toTable(),
                remoteKeyLocalDataSource,
                mMovieLocalDataSource,
                mMovieRemoteDataSource
            ),
            pagingSourceFactory = {
                mMovieLocalDataSource.getMoviesPagingSourceFactory(movieType.toTable())
            }
        )
            .flow
            .map { data ->
                data.map { movieTable ->
                    movieTable.toDomain()
                }
            }
    }

    override fun getRandomNowPlayingMovies(): Flow<List<Movie>> {
        return flow {
            fetchMoviesFromNetwork(1, MovieType.NowPlaying)
            emit(
                mMovieLocalDataSource
                    .getMovies(MovieTypeTable.NowPlaying)
                    .map(MovieTable::toDomain)
            )
        }.onStart {
            emit(
                mMovieLocalDataSource
                    .getMovies(MovieTypeTable.NowPlaying)
                    .map(MovieTable::toDomain)
            )
        }.catch {
            try {
                emit(
                    mMovieLocalDataSource
                        .getMovies(MovieTypeTable.NowPlaying)
                        .map(MovieTable::toDomain)
                )
            } catch (exception: Exception) {
                throw exception
            }
        }.distinctUntilChanged()
    }

    private suspend fun fetchMoviesFromNetwork(page: Int, movieType: MovieType): List<Movie> {
        val moviesDto = mMovieRemoteDataSource.getMovies(page, movieType.toParam())
        mMovieLocalDataSource.deleteAllMovies(movieType.toTable())
        mMovieLocalDataSource.insertAllMovies(moviesDto.map { it.toTable(movieType.toTable()) })
        return moviesDto.map { it.toDomain() }
    }

    override fun getMovie(movieId: Long): Flow<Movie> {
        return flow {
            fetchMovieFromNetwork(movieId)
            emit(mMovieLocalDataSource.getMovie(movieId).toDomain())
        }.onStart {
            emit(mMovieLocalDataSource.getMovie(movieId).toDomain())
        }.catch {
            try {
                emit(mMovieLocalDataSource.getMovie(movieId).toDomain())
            } catch (exception: Exception) {
                throw exception
            }
        }.distinctUntilChanged()
    }

    private suspend fun fetchMovieFromNetwork(movieId: Long): Movie {
        val movieDto = mMovieRemoteDataSource.getMovie(movieId)
        mMovieLocalDataSource.deleteMovie(movieId)
        mMovieLocalDataSource.insertMovie(movieDto.toTable())
        return movieDto.toDomain()
    }

    override fun getMovieReviews(movieId: Long): Flow<List<MovieReview>> {
        return flow {
            getMovieReviewsFromNetwork(movieId)
            emit(mMovieLocalDataSource.getMovieReviews(movieId).map(ReviewTable::toDomain))
        }.onStart {
            emit(mMovieLocalDataSource.getMovieReviews(movieId).map(ReviewTable::toDomain))
        }.catch {
            try {
                emit(mMovieLocalDataSource.getMovieReviews(movieId).map(ReviewTable::toDomain))
            } catch (exception: Exception) {
                throw exception
            }
        }.distinctUntilChanged()
    }

    private suspend fun getMovieReviewsFromNetwork(movieId: Long): List<MovieReview> {
        val movieReviewsDto = mMovieRemoteDataSource.getMovieReviews(movieId)
        mMovieLocalDataSource.deleteMovieReviews(movieId)
        mMovieLocalDataSource.insertMovieReviews(movieReviewsDto.map { it.toTable(movieId) })

        return movieReviewsDto.map(MovieReviewDto::toDomain)
    }

    override fun getMovieFavorite(movieId: Long): Flow<MovieFavorite> {
        return mMovieLocalDataSource
            .getMovieFavorite(movieId)
            .map {
                it.toDomain()
            }
            .distinctUntilChanged()
    }

    override suspend fun saveToFavorites(movieId: Long, isFavorite: Boolean): Unit {
        return mMovieLocalDataSource
            .getMovieFavorite(movieId)
            .firstOrNull()
            .let { movieFavorite ->
                movieFavorite?.favoriteTable?.let {
                    mMovieLocalDataSource.updateMovieFavorite(movieId, isFavorite)
                } ?: mMovieLocalDataSource.insertMovieFavorite(movieId, isFavorite)
            }
    }

    override fun getMovieTrailers(movieId: Long): Flow<List<MovieTrailer>> {
        return flow {
            fetchMovieTrailersFromNetwork(movieId)
            emit(mMovieLocalDataSource.getMovieTrailers(movieId).map(TrailerTable::toDomain))
        }.onStart {
            emit(mMovieLocalDataSource.getMovieTrailers(movieId).map(TrailerTable::toDomain))
        }.catch {
            try {
                emit(mMovieLocalDataSource.getMovieTrailers(movieId).map(TrailerTable::toDomain))
            } catch (exception: Exception) {
                throw exception
            }
        }.distinctUntilChanged()
    }

    private suspend fun fetchMovieTrailersFromNetwork(movieId: Long): List<MovieTrailer> {
        val movieTrailersDto = mMovieRemoteDataSource.getMovieTrailers(movieId)
        mMovieLocalDataSource.deleteMovieTrailers(movieId)
        mMovieLocalDataSource.insertMovieTrailers(movieTrailersDto.map { trailer ->
            trailer.toTable(
                trailer.id,
                movieId
            )
        })

        return movieTrailersDto.map(MovieTrailerDto::toDomain)
    }
}