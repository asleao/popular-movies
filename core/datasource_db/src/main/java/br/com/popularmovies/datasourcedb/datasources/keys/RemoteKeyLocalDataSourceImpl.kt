package br.com.popularmovies.datasourcedb.datasources.keys

import androidx.room.withTransaction
import br.com.popularmovies.core.api.RemoteKeyLocalDataSource
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.core.api.models.keys.RemoteKeyTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import javax.inject.Inject

class RemoteKeyLocalDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    RemoteKeyLocalDataSource {
    private val dao = appDatabase.remoteKeysDao()

    override suspend fun clear() {
        appDatabase.withTransaction {
            dao.clearRemoteKeys()
        }
    }

    override suspend fun upsertAll(keys: List<RemoteKeyTable>) {
        appDatabase.withTransaction {
            dao.upsertAll(keys)
        }
    }

    override suspend fun remoteKeyId(id: Long, type: MovieTypeTable): RemoteKeyTable? {
        return dao.remoteKeyId(id, type)
    }
}