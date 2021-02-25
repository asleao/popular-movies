package br.com.popularmovies.services.movieService.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate
import java.math.BigDecimal

@Entity(tableName = "movie")
@Parcelize
@JsonClass(generateAdapter = true)
data class MovieDto(
        @Json(name = "vote_count")
        val votes: Int ,
        @PrimaryKey(autoGenerate = true)
        @Json(name = "id")
        var id: Int = -1,
        @Json(name = "vote_average")
        val voteAverage: BigDecimal,
        @Json(name = "original_title")
        val originalTitle: String,
        @Json(name = "popularity")
        val popularity: BigDecimal,
        @Json(name = "poster_path")
        val poster: String,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "release_date")
        val releaseDate: LocalDate,
        var isFavorite: Boolean = false
) : Parcelable
