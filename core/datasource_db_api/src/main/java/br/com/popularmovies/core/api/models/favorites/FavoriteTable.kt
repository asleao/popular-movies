package br.com.popularmovies.core.api.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDateTime

@Entity(tableName = "favorite")
data class FavoriteTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val movieRemoteId: Long,
    val isFavorite: Boolean,
    val updatedAt: LocalDateTime
)