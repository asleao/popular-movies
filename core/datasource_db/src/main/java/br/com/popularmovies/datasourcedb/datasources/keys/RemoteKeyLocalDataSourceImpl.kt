package br.com.popularmovies.datasourcedb.datasources.keys

import androidx.room.withTransaction
import br.com.popularmovies.datasourcedb.AppDatabase
import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import javax.inject.Inject

class RemoteKeyLocalDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    RemoteKeyLocalDataSource {
    private val dao = appDatabase.remoteKeysDao()

    override suspend fun clear() {
        appDatabase.withTransaction {
            dao.clearRemoteKeys()
        }
    }

    override suspend fun insertAll(keys: List<RemoteKeyTable>) {
        appDatabase.withTransaction {
            dao.insertAll(keys)
        }
    }

    override suspend fun remoteKeyId(id: Long, type: MovieTypeTable): RemoteKeyTable? {
        return dao.remoteKeyId(id, type)
    }
}