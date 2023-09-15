package br.com.popularmovies.core.api.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val movieId: Long,
    val isFavorite: Boolean,
    val isSynced: Boolean
)