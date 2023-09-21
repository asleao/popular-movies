package br.com.popularmovies.core.api.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import br.com.popularmovies.core.api.models.favorites.FavoriteTable
import br.com.popularmovies.core.api.models.movie.MovieTable

data class MovieAndFavorite(
    @Embedded val movie: MovieTable,
    @Relation(
        parentColumn = "remoteId",
        entityColumn = "movieRemoteId"
    )
    val favoriteTable: FavoriteTable?
)