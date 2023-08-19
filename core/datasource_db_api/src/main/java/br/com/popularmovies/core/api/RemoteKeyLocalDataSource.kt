package br.com.popularmovies.core.api

import br.com.popularmovies.core.api.models.keys.RemoteKeyTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable


interface RemoteKeyLocalDataSource {
    suspend fun clear()
    suspend fun insertAll(keys: List<RemoteKeyTable>)
    suspend fun remoteKeyId(id: Long, type: MovieTypeTable): RemoteKeyTable?
}