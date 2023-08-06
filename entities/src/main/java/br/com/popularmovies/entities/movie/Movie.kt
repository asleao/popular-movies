package br.com.popularmovies.entities.movie

import org.joda.time.LocalDate
import java.math.BigDecimal

data class Movie(
    val votes: Int,
    var id: Long,
    val type: MovieType,
    val voteAverage: BigDecimal,
    val originalTitle: String,
    val popularity: BigDecimal,
    val poster: String,
    val overview: String,
    val releaseDate: LocalDate,
    var isFavorite: Boolean
)