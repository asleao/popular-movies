package br.com.popularmovies.entities.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.time.LocalDate

@Parcelize
data class Movie(
        val votes: Int,
        var id: Int = -1,
        val voteAverage: BigDecimal,
        val originalTitle: String,
        val popularity: BigDecimal,
        val poster: String,
        val overview: String,
        val releaseDate: LocalDate,
        var isFavorite: Boolean = false
) : Parcelable
