package br.com.popularmovies.model.movie

import org.joda.time.LocalDateTime

data class MovieFavorite(
    var id: Long,
    val isFavorite: Boolean,
    val updatedAt: LocalDateTime?
)