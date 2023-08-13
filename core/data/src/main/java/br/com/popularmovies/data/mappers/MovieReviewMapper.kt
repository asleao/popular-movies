package br.com.popularmovies.data.mappers

import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.model.movie.MovieReview

fun MovieReviewDto.toDomain(): MovieReview {
    return MovieReview(
        author = author,
        content = content
    )
}
