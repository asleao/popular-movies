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
import br.com.popularmovies.datasourceremote.repositories.movie.MovieRemoteDataSource
import br.com.popularmovies.repositories.mappers.toTable
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PopularMoviesRemoteMediator(
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
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
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
            return when (val result = movieRemoteDataSource.getPopularMovies(page)) {
                is Result.Error -> MediatorResult.Error(Throwable("${result.error.code}-${result.error.message}"))
                is Result.Success -> {
                    val endOfPaginationReached = result.data.isEmpty()
                    if (loadType == LoadType.REFRESH) {
                        remoteKeyLocalDataSource.clear()
                        movieLocalDataSource.deleteAllMovies()
                    }
                    val prevKey = if (page == START_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = result.data.map {
                        RemoteKeyTable(
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    remoteKeyLocalDataSource.insertAll(keys)
                    // MovieDb api returns movie primary key with random values. If we use these ids
                    // we lose the sort from the server and also cause a pagination loop inside this method.
                    // The problem here is that we need to keep the same ids between remote_keys and movie
                    // table. For now, these logic works but would be nice to see if I can improve that.
                    
                    val keysIds = remoteKeyLocalDataSource.getAll()
                        .filter { it.prevKey == prevKey && it.nextKey == nextKey }
                        .map { it.id }
                    movieLocalDataSource.insertAllMovies(result.data.mapIndexed { index, movie ->
                        movie.toTable(keysIds[index])
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

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieTable>): RemoteKeyTable? {
        return state.pages
            .lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()
            ?.let { movie ->
                remoteKeyLocalDataSource.remoteKeyId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieTable>): RemoteKeyTable? {
        return state.pages
            .firstOrNull {
                it.data.isNotEmpty()
            }?.data?.firstOrNull()
            ?.let { movie ->
                remoteKeyLocalDataSource.remoteKeyId(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieTable>
    ): RemoteKeyTable? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyLocalDataSource.remoteKeyId(id)
            }
        }
    }

    companion object {
        private const val START_INDEX = 1
    }
}