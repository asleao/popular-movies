package br.com.popularmovies.datasourcedb.datasources.keys

import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable

interface RemoteKeyLocalDataSource {
    suspend fun clear()
    suspend fun insertAll(keys: List<RemoteKeyTable>)
    suspend fun remoteKeyId(id: Long, type: MovieTypeTable): RemoteKeyTable?
}