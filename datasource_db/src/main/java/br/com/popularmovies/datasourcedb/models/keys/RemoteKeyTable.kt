package br.com.popularmovies.datasourcedb.models.keys

import androidx.room.Entity
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable

@Entity(tableName = "remote_keys", primaryKeys = ["movieId", "type"])
data class RemoteKeyTable(
    val movieId: Long,
    val type: MovieTypeTable,
    val prevKey: Int?,
    val nextKey: Int?
)