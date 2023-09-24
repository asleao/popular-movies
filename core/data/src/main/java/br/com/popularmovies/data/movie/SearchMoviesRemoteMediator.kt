package br.com.popularmovies.data.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.core.api.RemoteKeyLocalDataSource
import br.com.popularmovies.core.api.models.keys.RemoteKeyTable
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.data.mappers.toTable
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource

@ExperimentalPagingApi
class SearchMoviesRemoteMediator(
    private val query: String,
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : RemoteMediator<Int, MovieTable>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieTable>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys =
                    getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: START_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            return movieRemoteDataSource.searchMovies(page, query)
                .filter { it.poster != null }
                .let { data ->
                    val endOfPaginationReached = data.isEmpty()

                    if (loadType == LoadType.REFRESH) {
                        remoteKeyLocalDataSource.clear()
                        movieLocalDataSource.deleteAllMovies(MovieTypeTable.Search)
                    }

                    remoteKeyLocalDataSource.clear()

                    val prevKey = if (page == START_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = data.map {
                        RemoteKeyTable(
                            movieId = it.id,
                            type = MovieTypeTable.Search,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    remoteKeyLocalDataSource.insertAll(keys)
                    movieLocalDataSource.insertAllMovies(data.map { movie ->
                        movie.toTable(MovieTypeTable.Search)
                    })
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieTable>
    ): RemoteKeyTable? {
        return state.pages
            .lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()
            ?.let { movie ->
                remoteKeyLocalDataSource.remoteKeyId(movie.remoteId, MovieTypeTable.Search)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieTable>
    ): RemoteKeyTable? {
        return state.pages
            .firstOrNull {
                it.data.isNotEmpty()
            }?.data?.firstOrNull()
            ?.let { movie ->
                remoteKeyLocalDataSource.remoteKeyId(movie.remoteId, MovieTypeTable.Search)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieTable>
    ): RemoteKeyTable? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.remoteId?.let { id ->
                remoteKeyLocalDataSource.remoteKeyId(id, MovieTypeTable.Search)
            }
        }
    }

    companion object {
        private const val START_INDEX = 1
    }
}