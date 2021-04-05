package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate
import java.math.BigDecimal

@Entity(tableName = "movie")
@Parcelize
data class MovieTable(
        val votes: Int,
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        val voteAverage: BigDecimal,
        val originalTitle: String,
        val popularity: BigDecimal,
        val poster: String,
        val overview: String,
        val releaseDate: LocalDate,
        var isFavorite: Boolean = false
) : Parcelable
