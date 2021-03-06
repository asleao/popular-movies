package br.com.popularmovies.datanetwork.models.movie

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate
import java.math.BigDecimal

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieDto(
        @Json(name = "vote_count")
        val votes: Int,
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
