package br.com.popularmovies.datasourcedb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import br.com.popularmovies.core.api.models.keys.RemoteKeyTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable

@Dao
interface RemoteKeysDao {

    @Upsert
    suspend fun upsertAll(remoteKey: List<RemoteKeyTable>)

    @Query("SELECT DISTINCT * FROM remote_keys WHERE movieId=:movieId and type=:type")
    suspend fun remoteKeyId(movieId: Long, type: MovieTypeTable): RemoteKeyTable?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}