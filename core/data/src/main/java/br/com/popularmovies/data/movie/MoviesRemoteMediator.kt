package br.com.popularmovies.data.movie

//import retrofit2.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.core.api.MovieLocalDataSource
import br.com.popularmovies.core.api.RemoteKeyLocalDataSource
import br.com.popularmovies.core.api.models.keys.RemoteKeyTable
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.data.mappers.toParam
import br.com.popularmovies.data.mappers.toTable
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import java.io.IOException

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val movieType: MovieTypeTable,
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
                    getRemoteKeyClosestToCurrentPosition(state, movieType)
                remoteKeys?.nextKey?.minus(1) ?: START_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state, movieType)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state, movieType)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
           return movieRemoteDataSource.getMovies(page, movieType.toParam()).let { data ->

//                is Result.Error -> MediatorResult.Error(Throwable("${result.error.code}-${result.error.message}"))
                val endOfPaginationReached = data.isEmpty()
                if (loadType == LoadType.REFRESH) {
                    remoteKeyLocalDataSource.clear()
                    movieLocalDataSource.deleteAllMovies(movieType)
                }
                val prevKey = if (page == START_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = data.map {
                    RemoteKeyTable(
                        movieId = it.id,
                        type = movieType,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                remoteKeyLocalDataSource.insertAll(keys)
                movieLocalDataSource.insertAllMovies(data.map { movie ->
                    movie.toTable(movieType)
                })
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        }
//        catch (exception: HttpException) {
//            return MediatorResult.Error(exception)
//        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieTable>,
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
        state: PagingState<Int, MovieTable>,
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
        state: PagingState<Int, MovieTable>, type: MovieTypeTable
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