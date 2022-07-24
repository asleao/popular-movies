package br.com.popularmovies.repositories.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourcedb.datasources.keys.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.datasources.movie.MovieLocalDataSource
import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremote.models.movie.MovieTypeParam
import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.repositories.mappers.toMostPopularTable
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PopularMoviesRemoteMediator(
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : RemoteMediator<Int, MovieTable.MostPopular>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieTable.MostPopular>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys =
                    getRemoteKeyClosestToCurrentPosition(state, MovieTypeTable.MostPopular)
                remoteKeys?.nextKey?.minus(1) ?: START_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state, MovieTypeTable.MostPopular)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state, MovieTypeTable.MostPopular)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            return when (val result =
                movieRemoteDataSource.getMovies(page, MovieTypeParam.MostPopular)) {
                is Result.Error -> MediatorResult.Error(Throwable("${result.error.code}-${result.error.message}"))
                is Result.Success -> {
                    val endOfPaginationReached = result.data.isEmpty()
                    if (loadType == LoadType.REFRESH) {
                        remoteKeyLocalDataSource.clear()
                        movieLocalDataSource.deleteAllMostPopularMovies()
                    }
                    val prevKey = if (page == START_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = result.data.map {
                        RemoteKeyTable(
                            movieId = it.id,
                            type = MovieTypeTable.MostPopular,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    remoteKeyLocalDataSource.insertAll(keys)
                    movieLocalDataSource.insertAllMostPopularMovies(result.data.map { movie ->
                        movie.toMostPopularTable()
                    })
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieTable.MostPopular>,
        type: MovieTypeTable
    ): RemoteKeyTable? {
        return state.pages
            .lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()
            ?.let { movie ->
                remoteKeyLocalDataSource.remoteKeyId(movie.remoteId, type)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieTable.MostPopular>,
        type: MovieTypeTable
    ): RemoteKeyTable? {
        return state.pages
            .firstOrNull {
                it.data.isNotEmpty()
            }?.data?.firstOrNull()
            ?.let { movie ->
                remoteKeyLocalDataSource.remoteKeyId(movie.remoteId, type)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieTable.MostPopular>, type: MovieTypeTable
    ): RemoteKeyTable? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { id ->
                remoteKeyLocalDataSource.remoteKeyId(id, type)
            }
        }
    }

    companion object {
        private const val START_INDEX = 1
    }
}