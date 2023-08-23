package br.com.popularmovies.core.api.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.trailer.TrailerTable

data class MovieWithTrailersRelation(
    @Embedded val movie: MovieTable,
    @Relation(parentColumn = "remoteId", entityColumn = "movieId")
    val trailers: List<TrailerTable>
)
