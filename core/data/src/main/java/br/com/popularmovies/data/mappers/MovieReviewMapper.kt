package br.com.popularmovies.data.mappers

import br.com.popularmovies.core.api.models.reviews.ReviewTable
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.model.movie.MovieReview

fun MovieReviewDto.toDomain(): MovieReview {
    return MovieReview(
        author = author,
        content = content
    )
}

fun MovieReviewDto.toTable(movieId: Long): ReviewTable {
    return ReviewTable(
        movieId = movieId,
        author = author,
        content = content
    )
}

fun ReviewTable.toDomain(): MovieReview {
    return MovieReview(
        author = author,
        content = content
    )
}
