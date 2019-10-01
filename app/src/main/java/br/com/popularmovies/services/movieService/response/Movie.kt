package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate
import java.math.BigDecimal

@Entity(tableName = "movie")
@Parcelize
data class Movie(
    @field:Json(name = "vote_count")
    val votes: Int,
    @PrimaryKey(autoGenerate = true)
    @field:Json(name = "id")
    var id: Int = -1,
    @field:Json(name = "vote_average")
    val voteAverage: BigDecimal,
    @field:Json(name = "original_title")
    val originalTitle: String,
    @field:Json(name = "popularity")
    val popularity: BigDecimal,
    @field:Json(name = "poster_path")
    val poster: String,
    @field:Json(name = "overview")
    val overview: String,
    @field:Json(name = "release_date")
    val releaseDate: LocalDate,
    var isFavorite: Boolean = false
) : Parcelable
