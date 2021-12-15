package br.com.popularmovies.datasourcedb.datasources.keys

import androidx.room.withTransaction
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable
import javax.inject.Inject

class RemoteKeyLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    private val dao = appDatabase.remoteKeysDao()

    suspend fun clear() {
        appDatabase.withTransaction {
            dao.clearRemoteKeys()
        }
    }

    suspend fun insertAll(keys: List<RemoteKeyTable>) {
        appDatabase.withTransaction {
            dao.insertAll(keys)
        }
    }

    suspend fun remoteKeyId(id: Long): RemoteKeyTable? {
        return dao.remoteKeyId(id)
    }
}