package br.com.popularmovies.data.movie

import androidx.paging.*
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.core.api.RemoteKeyLocalDataSource
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.core.api.models.reviews.ReviewTable
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.data.config.PaginationConfig
import br.com.popularmovies.data.mappers.toDomain
import br.com.popularmovies.data.mappers.toTable
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.movie.MovieTrailer
import br.com.popularmovies.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
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
        return mMovieLocalDataSource.getMovies(MovieTypeTable.NowPlaying)
            .map { it.map(MovieTable::toDomain) }
    }

    override fun getMovie(movieId: Long): Flow<Movie> {
        return flow {
            emit(getMovieFromNetwork(movieId))
        }.onStart {
            mMovieLocalDataSource.getMovie(movieId)?.let { movie ->
                emit(movie.toDomain())
            }
        }.catch { exception ->
            mMovieLocalDataSource.getMovie(movieId)?.let { movie ->
                emit(movie.toDomain())
            } ?: throw exception
        }.distinctUntilChanged()
    }

    private suspend fun getMovieFromNetwork(movieId: Long): Movie {
        val movieDto = mMovieRemoteDataSource.getMovie(movieId)
        mMovieLocalDataSource.deleteMovie(movieId)
        mMovieLocalDataSource.insertMovie(movieDto.toTable())
        return movieDto.toDomain()
    }

    override fun getMovieReviews(movieId: Long): Flow<List<MovieReview>> {
        return flow {
            emit(getMovieReviewsFromNetwork(movieId))
        }.onStart {
            val movieReviews =
                mMovieLocalDataSource.getMovieReviews(movieId)?.map(ReviewTable::toDomain)
            movieReviews?.let { emit(it) }
        }.catch { exception ->
            mMovieLocalDataSource.getMovieReviews(movieId)?.map(ReviewTable::toDomain)
                ?: throw exception
        }.distinctUntilChanged()
    }

    private suspend fun getMovieReviewsFromNetwork(movieId: Long): List<MovieReview> {
        val movieReviewsDto = mMovieRemoteDataSource.getMovieReviews(movieId)
        mMovieLocalDataSource.deleteMovieReviews(movieId)
        mMovieLocalDataSource.insertMovieReviews(movieReviewsDto.map { it.toTable(movieId) })

        return movieReviewsDto.map(MovieReviewDto::toDomain)
    }

    override fun saveToFavorites(movie: Movie) {
        //TODO to be implemented
    }

    override fun getMovieTrailers(movieId: Long): Flow<List<MovieTrailer>> {
        return mMovieRemoteDataSource
            .getMovieTrailers(movieId)
            .map { it.map(MovieTrailerDto::toDomain) }
    }
}