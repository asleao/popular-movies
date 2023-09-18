package br.com.popularmovies.data.mappers

import br.com.popularmovies.core.api.models.relations.MovieAndFavorite
import br.com.popularmovies.model.movie.MovieFavorite

fun MovieAndFavorite.toDomain(): MovieFavorite {
    return MovieFavorite(
        id = movie.remoteId,
        isFavorite = favoriteTable?.isFavorite ?: false,
        updatedAt = favoriteTable?.updatedAt
    )
}