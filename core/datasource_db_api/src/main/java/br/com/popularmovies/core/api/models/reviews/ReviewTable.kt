package br.com.popularmovies.core.api.models.reviews

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val movieId: Long,
    val author: String = "",
    val content: String = ""
)