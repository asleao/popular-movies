package br.com.popularmovies.datasourcedb.models.keys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val prevKey: Int?,
    val nextKey: Int?
)