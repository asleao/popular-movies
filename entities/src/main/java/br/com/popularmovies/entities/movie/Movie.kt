package br.com.popularmovies.entities.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate
import java.math.BigDecimal

@Parcelize
data class Movie(
        val votes: Int,
        var id: Long,
        val voteAverage: BigDecimal,
        val originalTitle: String,
        val popularity: BigDecimal,
        val poster: String,
        val overview: String,
        val releaseDate: LocalDate,
        var isFavorite: Boolean
) : Parcelable
