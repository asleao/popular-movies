package br.com.popularmovies.core.api.models.trailer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trailers")
data class TrailerTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val remoteId: String,
    val movieId: Long,
    val name: String,
    val key: String
)