package br.com.popularmovies.core.api.models.keys

import androidx.room.Entity
import br.com.popularmovies.core.api.models.movie.MovieTypeTable

@Entity(tableName = "remote_keys", primaryKeys = ["movieId", "type"])
data class RemoteKeyTable(
    val movieId: Long,
    val type: MovieTypeTable,
    val prevKey: Int?,
    val nextKey: Int?
)