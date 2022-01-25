package br.com.popularmovies.datasourcedb.models.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDate
import java.math.BigDecimal

@Entity(tableName = "movie")
data class MovieTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var remoteId: Long = 0,
    val voteAverage: BigDecimal,
    val originalTitle: String,
    val popularity: BigDecimal,
    val poster: String,
    val overview: String,
    val releaseDate: LocalDate,
    val votes: Int,
    var isFavorite: Boolean = false,
    val type: MovieTypeTable
)