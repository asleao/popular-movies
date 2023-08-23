package br.com.popularmovies.core.api.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.review.ReviewTable

data class MovieWithReviewsRelation(
    @Embedded val movie: MovieTable,
    @Relation(parentColumn = "remoteId", entityColumn = "movieId")
    val reviews: List<ReviewTable>
)