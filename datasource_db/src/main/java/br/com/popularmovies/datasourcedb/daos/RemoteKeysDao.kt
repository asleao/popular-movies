package br.com.popularmovies.datasourcedb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys")
    suspend fun getAll(): List<RemoteKeyTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyTable>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun remoteKeyId(id: Long): RemoteKeyTable?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}