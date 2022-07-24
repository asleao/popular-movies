package br.com.popularmovies.datasourcedb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyTable>)

    @Query("SELECT * FROM remote_keys WHERE movieId=:movieId and type=:type")
    suspend fun remoteKeyId(movieId: Long, type: MovieTypeTable): RemoteKeyTable?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}