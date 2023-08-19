package br.com.popularmovies.datasourceremoteapi.models.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.joda.time.LocalDate
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "vote_count")
    val votes: Int,
    @Json(name = "id")
    var id: Long = 0,
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
)
